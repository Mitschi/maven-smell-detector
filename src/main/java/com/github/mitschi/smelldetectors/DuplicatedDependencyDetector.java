package com.github.mitschi.smelldetectors;

import com.github.mitschi.smells.MavenSmell;
import com.github.mitschi.smells.MavenSmellType;

import java.util.ArrayList;
import java.util.List;

public class DuplicatedDependencyDetector extends AbstractSmellDetector {
    public MavenSmellType canDetect = MavenSmellType.DUPLICATED_DEPENDENCY;

    public List<MavenSmell> detectSmells() {
        List<MavenSmell> smells = new ArrayList<>();
        //TODO implement here
        return smells;
    }
}
