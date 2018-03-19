package com.github.mitschi.smelldetectors;

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

    private static List<Dependency> dependencyList;
    private static List<MavenSmell> smells;

    private void getDuplicateDependencies(Model model, String file) {

        Model.Dependencies dependencies = model.getDependencies();

        List<String> depStrings = new ArrayList<>();

        for(Dependency d : dependencies.getDependency()) {
            // create a identification-string for each dependency
            String s = d.getGroupId() + "." + d.getArtifactId();

            // if this string is already in our depStrings-list, the dependency is a duplicate
            if(depStrings.contains(s)) {
                this.smells.add(new MavenSmell(MavenSmellType.DUPLICATED_DEPENDENCY, new File(file)));
            }

            depStrings.add(s);

            // add to global dependency-list
            dependencyList.add(d);
        }



    }

    public List<MavenSmell> detectSmells() {
        smells = new ArrayList<>();
        this.dependencyList = new ArrayList<>();

        // start with the root-pom
        Model rootModel = this.pomTree.getRoot().getData();

        // add all dependencies to the global list for possible duplicates in the sub-pom
        getDuplicateDependencies(rootModel, this.pomTree.getRoot().getFile());






        // process other sub-poms



//        // the list of duplicated dependencies
//        List<Dependency> duplicateList = new ArrayList<>();
//
//
//        Iterator it = this.pomModelMapFromFile.entrySet().iterator();
//
//        while (it.hasNext()) {
//
//            Map.Entry entry = (Map.Entry)it.next();
//
//            Model m = (Model)entry.getValue();
//
//            Model.Dependencies depList = m.getDependencies();
//
//            List<String> depStrings = new ArrayList<>();
//
//            // iterate over all dependencies
//            for(Dependency dependency : depList.getDependency()) {
//                // create a identification-string for each dependency
//                String s = dependency.getGroupId() + "." + dependency.getArtifactId();
//
//                // if this string is already in our depStrings-list, the dependency is a duplicate
//                if(depStrings.contains(s)) {
//                    duplicateList.add(dependency);
//                    smells.add(new MavenSmell(MavenSmellType.DUPLICATED_DEPENDENCY, new File(entry.getKey().toString())));
//                }
//
//                depStrings.add(s);
//            }
//
//
//            LOG.info("found " + duplicateList.size() + " duplicates");
//
////            int i = 1;
////            for(Dependency d : duplicateList) {
////                Log.info("#" + i++);
////                LOG.info("gID: " + d.getGroupId());
////                LOG.info("aID: " + d.getArtifactId());
////            }
//
//        }

        return smells;
    }
}
