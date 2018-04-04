package com.github.mitschi.smelldetectors;

import com.github.mitschi.common.PomTree;
import com.github.mitschi.smells.MavenSmell;
import com.github.mitschi.smells.MavenSmellType;
import org.apache.maven.pom._4_0.DependencyManagement;
import org.w3c.dom.Element;
import org.apache.maven.pom._4_0.Dependency;
import org.apache.maven.pom._4_0.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OffendingVersionsDetector extends AbstractSmellDetector {
    public MavenSmellType canDetect = MavenSmellType.OFFENDING_VERSIONS;

    private static final Logger LOG = LoggerFactory.getLogger(OffendingVersionsDetector.class);

    private List<MavenSmell> smells;

    private Map<String, List<Dependency>> dependencyToFileMap;
    private List<Dependency> totalDependencies;

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

        // if the current node has dependency-management
        if(node.getData().getDependencyManagement() != null) {
            DependencyManagement dpm = node.getData().getDependencyManagement();

            for(Dependency d : dpm.getDependencies().getDependency()) {

            }

        }

        if(node.getParent() != null) {
            return findVersionByProperty(node.getParent(), property);
        }

        return "NOT FOUND";
    }

    private void getAllDependencies(PomTree.Node<Model> node) {

        // if the node has dependencies
        if(node.getData().getDependencies() != null) {

            for(Dependency d : node.getData().getDependencies().getDependency()) {

                // check if version is a property-defined version
                if(d.getVersion()!= null && d.getVersion().startsWith("$")) {

//                    System.out.println(node.getFile());

//                    System.out.println("before: " + d.getVersion());

                    String realVersion = findVersionByProperty(node, d.getVersion());

//                    System.out.println("found: " + realVersion);

                    d.setVersion(realVersion);

//                    System.out.println("after: " + d.getVersion());

//                    System.out.println("---");
                }


                if(!totalDependencies.contains(d)) {
                    totalDependencies.add(d);
                }
            }

            dependencyToFileMap.put(node.getFile(), node.getData().getDependencies().getDependency());

        }

        // 2. process all child-nodes
        if(node.getChildren().size() > 0) {
            for(PomTree.Node<Model> child : node.getChildren()) {
                getAllDependencies(child);
            }
        }
    }



    private void checkOffendingVersionsViolation() {

        for (Map.Entry<String, List<Dependency>> entry : dependencyToFileMap.entrySet()) {
            String file = entry.getKey();             // current pom filepath
            List<Dependency> deps = entry.getValue(); // dependencies of the current pom

            for (Dependency dep : deps) {
                for (Dependency totalDep : totalDependencies) {
                    if(totalDep.getGroupId().equals(dep.getGroupId()) &&
                            totalDep.getArtifactId().equals(dep.getArtifactId())) {

                        if(totalDep.getVersion() != null && dep.getVersion() != null && !totalDep.getVersion().equals(dep.getVersion())) {

//                            System.out.println();
//                            System.out.println(totalDep.toString());
//                            System.out.println(dep.toString());
//                            System.out.println();

                            smells.add(new MavenSmell(MavenSmellType.OFFENDING_VERSIONS, new File(file), dep));
                        }
                    }
                }
            }
        }

    }

    public List<MavenSmell> detectSmells() {
        smells = new ArrayList<>();

        totalDependencies = new ArrayList<>();
        dependencyToFileMap = new HashMap<>();

        // get all total dependencies of the project
        getAllDependencies(this.pomTree.getRoot());

        checkOffendingVersionsViolation();

        return smells;
    }
}
