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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SnapshotDependencyDetector extends AbstractSmellDetector {
    public MavenSmellType canDetect = MavenSmellType.SNAPSHOT_DEPENDENCY;

    private static final Logger LOG = LoggerFactory.getLogger(SnapshotDependencyDetector.class);

    private static List<MavenSmell> smells;

    public List<MavenSmell> detectSmells() {

        smells = new ArrayList<>();

        // start with the root-pom
        Model rootModel = this.pomTree.getRoot().getData();

        // if the root is a SNAPSHOT-project, there can't be any smells
        if(rootModel.getVersion() != null &&
                rootModel.getVersion().contains("SNAPSHOT")) {
            return smells;
        }

        // check for each dependency of the root
        for(Dependency dependency : rootModel.getDependencies().getDependency()) {
            if(dependency.getVersion().contains("SNAPSHOT")) {
                smells.add(new MavenSmell(MavenSmellType.SNAPSHOT_DEPENDENCY, new File(this.pomTree.getRoot().getFile())));
            }
        }

//        PomTree.Node<Model> node = this.pomTree.findNode(this.pomTree.getRoot(), null, "mavenrepoparser-client", null);

//        System.out.println(node.getFile().toString());

        // TODO: check children

        return smells;
    }
}
