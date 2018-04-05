package com.github.mitschi.smelldetectors;

import com.github.mitschi.common.PomTree;
import com.github.mitschi.smells.MavenSmell;
import com.github.mitschi.smells.MavenSmellType;
import org.apache.maven.pom._4_0.Dependency;
import org.apache.maven.pom._4_0.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

public class DuplicatedDependencyDetector extends AbstractSmellDetector {
    public MavenSmellType canDetect = MavenSmellType.DUPLICATED_DEPENDENCY;

    private static final Logger LOG = LoggerFactory.getLogger(DuplicatedDependencyDetector.class);

    private List<MavenSmell> smells;

    public void checkDuplicationViolation(PomTree.Node<Model> node, HashMap<String, List<Dependency>> parentDependencies) {

        // start at the root:

        // 1. check if this node itself has duplicates
        List<Dependency> depList = new ArrayList<>();

        // if it has dependencies defined
        if(node.getData().getDependencies() != null) {
            // iterate over them
            for(Dependency d : node.getData().getDependencies().getDependency()) {

                boolean alreadyInList = false;

                // iterate over all previously checked dependencies
                for(Dependency dep : depList) {
                    // if anyone is a duplicate, add the smell
                    if(dep.getGroupId().equals(d.getGroupId()) &&
                            dep.getArtifactId().equals(d.getArtifactId()) &&
                            !dep.equals(d)) {
                        this.smells.add(new MavenSmell(MavenSmellType.DUPLICATED_DEPENDENCY, new File(node.getFile()), d, new File(node.getFile()), dep));
                        alreadyInList = true;
                    }
                }

                // if the dependency is not in the list, add it
                if(!alreadyInList) {
                    depList.add(d);
                }
            }
        }

        // 2. check, if there is a duplication in any of his parents
        if(parentDependencies.size() > 0) {
            // for every parent
            for (Map.Entry<String, List<Dependency>> parent : parentDependencies.entrySet()) {

                String filePath = parent.getKey();

                List<Dependency> depsOfParent = parent.getValue();

                for(Dependency d : depsOfParent) {

                    if(depList.contains(d)) {
                        this.smells.add(new MavenSmell(MavenSmellType.DUPLICATED_DEPENDENCY, new File(node.getFile()), d, new File(filePath), d));
                    }

//                    for(Dependency dep : depList) {
//                        if(dep.getGroupId().equals(d.getGroupId()) &&
//                                dep.getArtifactId().equals(d.getArtifactId())) {
//                            this.smells.add(new MavenSmell(MavenSmellType.DUPLICATED_DEPENDENCY, new File(node.getFile()), dep, new File(filePath), d));
//                        }
//                    }
                }
            }
        }

        HashMap<String, List<Dependency>> newParentDependencies = (HashMap)parentDependencies.clone();

        // add all dependencies of the node to the parent-dependency-map
        if(node.getData().getDependencies() != null) {
            newParentDependencies.put(node.getFile(), node.getData().getDependencies().getDependency());
        }

        // 3. check if a child has the same dependency as this node
        if(node.getChildren().size() > 0) {
            for(PomTree.Node<Model> child : node.getChildren()) {
                checkDuplicationViolation(child, newParentDependencies);
            }
        }

    }

    public List<MavenSmell> detectSmells() {
        smells = new ArrayList<>();

        // start the detection
        checkDuplicationViolation(this.pomTree.getRoot(), new HashMap<>());

        return smells;
    }
}
