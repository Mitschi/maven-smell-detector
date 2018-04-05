package com.github.mitschi;

import com.github.mitschi.common.MavenPom;
import com.github.mitschi.common.PomTree;
import com.github.mitschi.smelldetectors.AbstractSmellDetector;
import com.github.mitschi.smelldetectors.DuplicatedDependencyDetector;
import com.github.mitschi.smelldetectors.OffendingVersionsDetector;
import com.github.mitschi.smelldetectors.SnapshotDependencyDetector;
import com.github.mitschi.smells.MavenSmell;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.maven.pom._4_0.Dependency;
import org.apache.maven.pom._4_0.DependencyManagement;
import org.apache.maven.pom._4_0.Model;

import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class MavenSmellDetector {

    private static final Logger LOG = LoggerFactory.getLogger(MavenSmellDetector.class);

    private File projectFolder;
    private Collection<File> pomFiles;
    private Map<String, Model> pomModelMapFromFile;
    private MavenPom rootMavenPom;
    private PomTree<Model> pomTree;

    private List<AbstractSmellDetector> registeredSmellDetectors = Arrays.asList(new AbstractSmellDetector[]{new DuplicatedDependencyDetector(), new SnapshotDependencyDetector(), new OffendingVersionsDetector()});

    public List<MavenSmell> detectSmells(File projectRoot) {
        this.projectFolder = projectRoot;
        init();
        List<MavenSmell> smells = detectRegisteredSmells(projectRoot);
        return smells;
    }

    private List<MavenSmell> detectRegisteredSmells(File projectRoot) {
        List<MavenSmell> smells = new ArrayList<>();
        for (AbstractSmellDetector registeredSmellDetector : registeredSmellDetectors) {
            registeredSmellDetector.setEnvironment(this.projectFolder,this.pomFiles,this.pomModelMapFromFile,this.rootMavenPom, this.pomTree);
            smells.addAll(registeredSmellDetector.detectSmells());
        }

        return smells;
    }

    private void init() {
        this.pomFiles = FileUtils.listFiles(this.projectFolder, new RegexFileFilter("pom\\.xml"), TrueFileFilter.INSTANCE);
        LOG.info("Preprocessing "+this.pomFiles.size()+" POMs");
        createPomModelMapMultiThread(8);
        mapModulesWithFiles();

        // ------------------------------------
        // create pom-tree model
        pomTree = new PomTree(rootMavenPom.getModel(), this.projectFolder + "/pom.xml");

        // add all sub-poms to tree
        Iterator it = this.pomModelMapFromFile.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();

            Model m = (Model) entry.getValue();

            // skip root node
            if(m.equals(rootMavenPom.getModel())) {
                continue;
            }

            PomTree.Node node = new PomTree.Node();
            node.setData(m);
            node.setFile(entry.getKey().toString());

            node.setParent(pomTree.getRoot());
            pomTree.getRoot().addChild(node);

        }



//        this.pomTree.printTree(this.pomTree.getRoot());

        List<PomTree.Node<Model>> copyOfChildren = new ArrayList<>();

        copyOfChildren.addAll(pomTree.getRoot().getChildren());

        for(PomTree.Node<Model> n : copyOfChildren) {

            if(n.getData().getParent() != null) {
                String parentGID = n.getData().getParent().getGroupId(); // the defined parent groupID in the pom.xml
                String parentAID = n.getData().getParent().getArtifactId(); // the defined parent artifactID in the pom.xml
                String parentVersion = n.getData().getParent().getVersion(); // the defined parent version in the pom.xml

                // if the parent-node in the tree does not match the defined parent in the pom.xml
                if(!n.getParent().getData().getGroupId().equals(parentGID) ||
                        !n.getParent().getData().getArtifactId().equals(parentAID) ||
                        !n.getParent().getData().getVersion().equals(parentVersion)) {

                    // find the new parent, starting from the root
                    PomTree.Node<Model> newParent = this.pomTree.findNode(this.pomTree.getRoot(), parentGID, parentAID, parentVersion);

                    // found the new parent
                    if(newParent != null) {
                        // remove the child-node from the old parent
                        n.getParent().removeChild(n);
                        // set the new parent
                        n.setParent(newParent);
                        // set the node as a childnode of the new parent
                        newParent.addChild(n);
                    }
                }
            }

        }

        // fill in data for the root
        fillIncompleteData(pomTree.getRoot());

        for(PomTree.Node<Model> node : pomTree.getRoot().getChildren()) {
            // fill in data for all children
            fillIncompleteData(node);
        }

//        this.pomTree.printTree(this.pomTree.getRoot());


        // ------------------------------------

        LOG.info("Finished preprocessing POMs");
    }

    private void fillIncompleteData(PomTree.Node<Model> node) {

        // set correct version-numbers

        if(node.getData().getDependencies() != null) {
            for (Dependency dep : node.getData().getDependencies().getDependency()) {

                if(dep.getVersion() == null) {
                    findVersionNumber(node, dep);
                }

                if(dep.getGroupId().startsWith("$")) {
                    String groupID = getProjectGroupID(node);
                    dep.setGroupId(groupID);
                }

                // check if version is a property-defined version
                if(dep.getVersion()!= null && dep.getVersion().startsWith("$")) {
                    String realVersion = findVersionByProperty(node, dep.getVersion());
                    dep.setVersion(realVersion);
                }



            }
        }

        if (node.getChildren().size() > 0) {
            for(PomTree.Node<Model> n : node.getChildren()) {
                fillIncompleteData(n);
            }
        }

    }

    private void findVersionNumber(PomTree.Node<Model> node, Dependency dependency) {


        // if the current node has dependency-management
        if(node.getData().getDependencyManagement() != null) {
            DependencyManagement dpm = node.getData().getDependencyManagement();

            for(Dependency d : dpm.getDependencies().getDependency()) {
                if(d.getGroupId().equals(dependency.getGroupId()) &&
                        d.getArtifactId().equals(dependency.getArtifactId()) &&
                        d.getVersion() != null) {
                    dependency.setVersion(d.getVersion());
                }
            }

        }

        if(node.getParent() != null) {
            findVersionNumber(node.getParent(), dependency);
        }
    }

    private String getProjectGroupID(PomTree.Node<Model> node) {

        if(node.getData().getGroupId() != null) {
            return node.getData().getGroupId();
        } else {
            if(node.getParent() != null) {
                return node.getData().getParent().getGroupId();
            } else {
                return "NOT FOUND";
            }
        }
    }

    private String findVersionByProperty(PomTree.Node<Model> node, String property) {

        // Project Model Variables
        if(property.equals("${project.version}")) {
            if(node.getData().getVersion() != null) {
                return node.getData().getVersion();
            } else {
                if(node.getParent() != null) {
                    findVersionByProperty(node.getParent(), property);
                }
            }
        }

        // if the current node has properties defined
        if(node.getData().getProperties() != null) {

            // iterate over all defined properties in this node
            for (int i = 0; i < node.getData().getProperties().getAny().size(); i++) {

                Element e = node.getData().getProperties().getAny().get(i);

                // if the property matches the defined placeholder
                if(e.getLocalName().equals(property.replaceAll("[${}]*",""))) {
                    return e.getFirstChild().getTextContent();
                }

            }


        }

        if(node.getParent() != null) {
            return findVersionByProperty(node.getParent(), property);
        }

        return "NOT FOUND";
    }

    protected void createPomModelMapMultiThread(int numThreads) {
        this.pomModelMapFromFile = new HashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        ArrayList<PomModelWorker> pomModelWorkers = new ArrayList<>();

//        LOG.info("Creating Pom Model Maps ("+this.pomFiles.size()+" files)");

        //Run 1 --> Model
        for (File file: this.pomFiles) {
            if ("pom.xml".equals(file.getName())) {
                PomModelWorker worker = new PomModelWorker(file);
                pomModelWorkers.add(worker);
                executor.execute(worker);
            }
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
        }

        //Run 2 --> assignment to maps
        for (PomModelWorker pomModelWorker : pomModelWorkers) {
            File file = pomModelWorker.getFile();
            Model model = pomModelWorker.getModel();
            if(model!=null) {
                this.pomModelMapFromFile.put(file.getAbsolutePath(), model);
            }
        }
    }

    private void mapModulesWithFiles() {
        File rootPom = new File(this.projectFolder,"pom.xml");
        Model rootModel= pomModelMapFromFile.get(rootPom.getAbsolutePath());
        MavenPom mavenPom = new MavenPom(rootModel,rootPom);
        mapModules(rootPom,mavenPom);
        this.rootMavenPom = mavenPom;
//        pomModelMapFromModuleDefinitionName=new HashMap<>();
//        //check root pom
//        File rootPom = new File(this.projectFolder,"pom.xml");
//
//        Model rootPomModel = pomModelMapFromFile.get(rootPom.getAbsolutePath());
//
//        if(rootPomModel.getModules()!=null) {
//            for(String moduleName : rootPomModel.getModules().getModule()) {
//                File moduleFolder = new File(rootPom.getParent(),moduleName);
//                File modulePom = new File(moduleFolder,"pom.xml");
//                Model moduleModel = this.pomModelMapFromFile.get(modulePom.getAbsolutePath());
//                if(moduleModel!=null) {
//                    pomModelMapFromModuleDefinitionName.put(moduleName, moduleModel);
//                } else {
//                    LOG.info("Couldn't find model for: "+moduleName);
//                }
//            }
//        }
    }

    private void mapModules(File rootPom, MavenPom mavenPom) {
        if(mavenPom.getModel().getModules()!=null) {
            for (String moduleName : mavenPom.getModel().getModules().getModule()) {
                File moduleFolder = new File(rootPom.getParent(),moduleName);
                File modulePom = new File(moduleFolder,"pom.xml");
                Model moduleModel = pomModelMapFromFile.get(modulePom.getAbsolutePath());
                if(moduleModel!=null) {
                    MavenPom moduleMavenPom = new MavenPom(moduleModel,modulePom);
                    mavenPom.getModules().add(moduleMavenPom);
                    mapModules(modulePom,moduleMavenPom);
                }
            }

        }
    }
}
