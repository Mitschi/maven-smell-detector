package com.github.mitschi;

import com.github.mitschi.common.MavenPom;
import com.github.mitschi.common.PomTree;
import com.github.mitschi.smelldetectors.AbstractSmellDetector;
import com.github.mitschi.smelldetectors.DuplicatedDependencyDetector;
import com.github.mitschi.smelldetectors.SnapshotDependencyDetector;
import com.github.mitschi.smells.MavenSmell;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.maven.pom._4_0.Model;

import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MavenSmellDetector {

    private static final Logger LOG = LoggerFactory.getLogger(MavenSmellDetector.class);

    private File projectFolder;
    private Collection<File> pomFiles;
    private Map<String, Model> pomModelMapFromFile;
    private MavenPom rootMavenPom;
    private PomTree<Model> pomTree;

    private List<AbstractSmellDetector> registeredSmellDetectors = Arrays.asList(new AbstractSmellDetector[]{new DuplicatedDependencyDetector(), new SnapshotDependencyDetector()});

    public List<MavenSmell> detectSmells(File projectRoot) {
        this.projectFolder=projectRoot;
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

            PomTree.Node node = new PomTree.Node();
            node.setData(m);
            node.setFile(entry.getKey().toString());

            pomTree.getRoot().addChild(node);

        }


        // set the correct parents for the children
        // TODO
        // ------------------------------------

        LOG.info("Finished preprocessing POMs");
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
                Thread.sleep(1000);
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
