package com.github.mitschi.smells;

// TODO: smelltype for duplicate in parent?

public enum MavenSmellType {
    DUPLICATED_DEPENDENCY,      //    S-D-1	Duplicated Dependency	A dependency is declared multiple times in a pom (or in a child of a parent pom)
    UNUSED_DEPENDENCY,          //    S-D-2	Unused Dependency	A dependency that is not use in the code
    SNAPSHOT_DEPENDENCY,        //    S-D-3	Snapshot Dependency	Usage of snapshot dependencies in not snapshot projects
    OFFENDING_VERSIONS          //    S-D-5	Offending Versions in Dependency	Dependency is declared in more than 1 pom but with different versions
}
