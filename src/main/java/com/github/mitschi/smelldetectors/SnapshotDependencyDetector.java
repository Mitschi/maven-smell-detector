package com.github.mitschi.smelldetectors;

import com.github.mitschi.common.PomTree;
import com.github.mitschi.smells.MavenSmell;
import com.github.mitschi.smells.MavenSmellType;
import org.apache.maven.pom._4_0.Dependency;
import org.apache.maven.pom._4_0.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SnapshotDependencyDetector extends AbstractSmellDetector {
    public MavenSmellType canDetect = MavenSmellType.SNAPSHOT_DEPENDENCY;

    private static final Logger LOG = LoggerFactory.getLogger(SnapshotDependencyDetector.class);

    private static List<MavenSmell> smells;

    /**
     * recursive funktion, which checks, if the current node or any of his children
     * contain a SNAPSHOT_DEPENDENCY-smell
     * @param node the node, which will be checked (and his children)
     */
    private void checkSnapshotViolation(PomTree.Node<Model> node) {

        // if the node has dependencies
        if(node.getData().getDependencies() != null) {
            // iterate over all defined dependencies
            for(Dependency dependency : node.getData().getDependencies().getDependency()) {
                // if the dependency is a SNAPSHOT-dependency, we found a smell
                if(dependency.getVersion().contains("SNAPSHOT")) {
                    smells.add(new MavenSmell(MavenSmellType.SNAPSHOT_DEPENDENCY, new File(this.pomTree.getRoot().getFile())));
                }
            }
        }

        // check all children of the node
        if(node.getChildren() != null) {
            for(PomTree.Node<Model> n : node.getChildren()) {
                checkSnapshotViolation(n);
            }
        }

    }

    /**
     * checks, if the project has any SNAPSHOT_DEPENDENCY-smells
     * @return a list of found SNAPSHOT_DEPENDENCY-smells
     */
    public List<MavenSmell> detectSmells() {

        smells = new ArrayList<>();

        // start with the root-pom
        Model rootModel = this.pomTree.getRoot().getData();

        // if the root is a SNAPSHOT-project, there can't be any smells
        if(rootModel.getVersion() != null &&
                rootModel.getVersion().contains("SNAPSHOT")) {
            return smells;
        }

        // start the check
        checkSnapshotViolation(this.pomTree.getRoot());

        return smells;
    }
}
