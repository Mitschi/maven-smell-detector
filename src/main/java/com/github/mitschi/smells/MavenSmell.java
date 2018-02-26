package com.github.mitschi.smells;

import java.io.File;

public class MavenSmell {
    private MavenSmellType mavenSmellType;
    private File location;

    public MavenSmell(){}
    public MavenSmell(MavenSmellType mavenSmellType, File location) {
        this.mavenSmellType = mavenSmellType;
        this.location = location;
    }

    public MavenSmellType getMavenSmellType() {
        return mavenSmellType;
    }

    public void setMavenSmellType(MavenSmellType mavenSmellType) {
        this.mavenSmellType = mavenSmellType;
    }

    @Override
    public String toString() {
        return "MavenSmell{" +
                "mavenSmellType=" + mavenSmellType +
                ", location=" + location +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MavenSmell that = (MavenSmell) o;

        if (mavenSmellType != that.mavenSmellType) return false;
        return location != null ? location.equals(that.location) : that.location == null;
    }

    @Override
    public int hashCode() {
        int result = mavenSmellType != null ? mavenSmellType.hashCode() : 0;
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }
}
