package com.github.mitschi.smells;

import java.io.File;

public class MavenSmell {
    private MavenSmellType mavenSmellType;
    private File location;
    private Object violator;
    private File violatesWithFile;
    private Object violatesWithObject;

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

    public MavenSmell(MavenSmellType mavenSmellType, File location, Object violator, File violatesWith, Object violatesWithObject) {
        this.mavenSmellType = mavenSmellType;
        this.location = location;
        this.violator = violator;
        this.violatesWithFile = violatesWith;
        this.violatesWithObject = violatesWithObject;
    }

    public MavenSmellType getMavenSmellType() {
        return mavenSmellType;
    }

    public void setMavenSmellType(MavenSmellType mavenSmellType) {
        this.mavenSmellType = mavenSmellType;
    }

    @Override
    public String toString() {

        String toPrint = "\r\n\tMavenSmell {";

        if(mavenSmellType != null) toPrint += String.format("\n\t\t %-20s = %s", "mavenSmellType", mavenSmellType);
        if(location != null) toPrint += String.format("\n\t\t %-20s = %s", "location", location);
        if(violator != null) toPrint += String.format("\n\t\t %-20s = %s", "violator", violator.toString());
        if(violatesWithFile != null) toPrint += String.format("\n\t\t %-20s = %s", "violatesWithFile", violatesWithFile.toString());
        if(violatesWithObject != null) toPrint += String.format("\n\t\t %-20s = %s", "violatesWithObject", violatesWithObject.toString());

        return toPrint + "\n\t}\n";
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
