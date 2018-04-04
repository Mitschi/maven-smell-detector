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

    public void checkDuplicationViolation(PomTree.Node<Model> node, List<Dependency> parentDependencies) {

        // 1. check if this node itself has duplicates
        List<String> depList = new ArrayList<>();

        // root-node
        if(parentDependencies.size() > 0) {
            for(Dependency d : parentDependencies) {
                depList.add(d.getGroupId() + "." + d.getArtifactId());
            }
        }

        // if the node has dependencies
        if(node.getData().getDependencies() != null) {
            for(Dependency d : node.getData().getDependencies().getDependency()) {
                String depID = d.getGroupId() + ":" + d.getArtifactId();



                // duplicate found
                if(depList.contains(depID)) {

                    System.out.println(depID);
                    System.out.println(node.getFile());
                    System.out.println();

                    this.smells.add(new MavenSmell(MavenSmellType.DUPLICATED_DEPENDENCY, new File(node.getFile()), d));
                } else {
                    depList.add(depID);
                }
            }
        }

        // 2. check if a child has the same dependency as this node
        if(node.getChildren().size() > 0) {
            if(node.getData().getDependencies() != null) {
                parentDependencies.addAll(node.getData().getDependencies().getDependency());
            }

            for(PomTree.Node<Model> child : node.getChildren()) {
                checkDuplicationViolation(child, parentDependencies);
            }

        }

    }

    public List<MavenSmell> detectSmells() {
        smells = new ArrayList<>();

        // start the detection
        checkDuplicationViolation(this.pomTree.getRoot(), new ArrayList<>());

        return smells;
    }
}
