package com.github.mitschi.smells;

import java.io.File;

public class MavenSmell {
    private MavenSmellType mavenSmellType;
    private File location;
    private Object violator;
    private File violatesWith; //TODO

    public MavenSmell(){}

    public MavenSmell(MavenSmellType mavenSmellType, File location) {
        this.mavenSmellType = mavenSmellType;
        this.location = location;
    }

    public MavenSmell(MavenSmellType mavenSmellType, File location, Object violator) {
        this.mavenSmellType = mavenSmellType;
        this.location = location;
        this.violator = violator;
    }

    public MavenSmellType getMavenSmellType() {
        return mavenSmellType;
    }

    public void setMavenSmellType(MavenSmellType mavenSmellType) {
        this.mavenSmellType = mavenSmellType;
    }

    @Override
    public String toString() {
        return "\nMavenSmell {" +
                "\n\tmavenSmellType = " + mavenSmellType +
                ",\n\tlocation = " + location +
                ",\n\tviolator = " + violator.toString() +
                "\n}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MavenSmell that = (MavenSmell) o;

        if (mavenSmellType != that.mavenSmellType) return false;
        if (!location.equals(that.location)) return false;
        if (violator != that.violator) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mavenSmellType != null ? mavenSmellType.hashCode() : 0;
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }
}
