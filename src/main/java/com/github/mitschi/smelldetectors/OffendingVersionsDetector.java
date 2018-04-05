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
//    private List<Dependency> totalDependencies;

    private void getAllDependencies(PomTree.Node<Model> node) {

        // if the node has dependencies
        if(node.getData().getDependencies() != null) {

//            for(Dependency d : node.getData().getDependencies().getDependency()) {
//
//                if(!totalDependencies.contains(d)) {
//                    totalDependencies.add(d);
//                }
//            }

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


            for (Map.Entry<String, List<Dependency>> pomToCheck : dependencyToFileMap.entrySet()) {
                // don't check the pom with itself
                if(!pomToCheck.equals(entry)) {

                    String fileOfSecondPom = pomToCheck.getKey();  // filepath of the second pom
                    List<Dependency> depsOfSecondPom = pomToCheck.getValue(); // dependencies of the second pom

                    for (Dependency dep : deps) {
                        for (Dependency dep2 : depsOfSecondPom) {

                            if(dep2.getGroupId().equals(dep.getGroupId()) &&
                                    dep2.getArtifactId().equals(dep.getArtifactId())) {

                                if(dep2.getVersion() != null && dep.getVersion() != null && !dep2.getVersion().equals(dep.getVersion())) {

                                    MavenSmell smell = new MavenSmell(MavenSmellType.OFFENDING_VERSIONS, new File(file), dep, new File(fileOfSecondPom), dep2);

                                    if(!smells.contains(smell) &&
                                            !smells.contains(smell.mirror())) {
                                        smells.add(smell);
                                    }

                                }
                            }

                        }
                    }
                }
            }

// --- old code, which does not note, which file violates with which other file
//            for (Dependency dep : deps) {
//                for (Dependency totalDep : totalDependencies) {
//                    if(totalDep.getGroupId().equals(dep.getGroupId()) &&
//                            totalDep.getArtifactId().equals(dep.getArtifactId())) {
//
//                        if(totalDep.getVersion() != null && dep.getVersion() != null && !totalDep.getVersion().equals(dep.getVersion())) {
////                            System.out.println(file);
////                            System.out.println(totalDep.toString());
////                            System.out.println(dep.toString());
////                            System.out.println("---");
//                            smells.add(new MavenSmell(MavenSmellType.OFFENDING_VERSIONS, new File(file), dep));
//                        }
//                    }
//                }
//            }
        }

    }

    public List<MavenSmell> detectSmells() {
        smells = new ArrayList<>();

//        totalDependencies = new ArrayList<>();
        dependencyToFileMap = new HashMap<>();

        // get all total dependencies of the project
        getAllDependencies(this.pomTree.getRoot());

        checkOffendingVersionsViolation();

        return smells;
    }
}
