package com.github.mitschi;

import com.github.mitschi.common.MavenPom;
import com.github.mitschi.common.PomTree;
import com.github.mitschi.smells.MavenSmell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public class Main
{
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main( String[] args )
    {

//        PomTree tree = new PomTree<>("asdf");
//
//        PomTree.Node child1 = new PomTree.Node();
//
//        tree.getRoot().addChild(child1);
//
//        child1.addChild(new PomTree.Node().setData("test"););

        File projectRoot = new File(args[0]);
        MavenSmellDetector mavenSmellDetector = new MavenSmellDetector();
        List<MavenSmell> smells = mavenSmellDetector.detectSmells(projectRoot);
        LOG.info("Found "+smells.size()+" smells in this project: "+smells);
    }
}
