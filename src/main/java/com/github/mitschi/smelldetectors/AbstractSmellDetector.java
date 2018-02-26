package com.github.mitschi.smelldetectors;


import com.github.mitschi.smells.MavenSmell;
import com.github.mitschi.smells.MavenSmellType;
import com.github.mitschi.common.MavenPom;
import org.apache.maven.pom._4_0.Model;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class AbstractSmellDetector {
    protected File projectFolder;
    protected Collection<File> pomFiles;
    protected Map<String, Model> pomModelMapFromFile;
    protected MavenPom rootMavenPom;

    protected MavenSmellType canDetect;

    public void setEnvironment(File projectFolder, Collection<File> pomFiles, Map<String, Model> pomModelMapFromFile, MavenPom rootMavenPom) {
        this.projectFolder = projectFolder;
        this.pomFiles = pomFiles;
        this.pomModelMapFromFile = pomModelMapFromFile;
        this.rootMavenPom = rootMavenPom;
    }

    public abstract List<MavenSmell> detectSmells();

    protected MavenSmellType getCanDetect() {
        return canDetect;
    }

    protected void setCanDetect(MavenSmellType canDetect) {
        this.canDetect = canDetect;
    }
}
