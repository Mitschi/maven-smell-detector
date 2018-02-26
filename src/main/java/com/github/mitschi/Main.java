package com.github.mitschi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public class Main
{
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main( String[] args )
    {
        File projectRoot = new File(args[0]);
        MavenSmellDetector mavenSmellDetector = new MavenSmellDetector();
        List<MavenSmell> smells = mavenSmellDetector.detectSmells(projectRoot);
        LOG.info("Found "+smells.size()+" smells in this project: "+smells);
    }
}
