package com.github.mitschi.common;

import org.apache.maven.pom._4_0.Model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MavenPom {
    private Model model;
    private File file;
    private List<MavenPom> modules=new ArrayList<>();
//    private MavenProjectMetrics mpm= new MavenProjectMetrics();

    public MavenPom(Model model, File file) {
        this.model = model;
        this.file = file;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public List<MavenPom> getModules() {
        return modules;
    }

    public void setModules(List<MavenPom> modules) {
        this.modules = modules;
    }

//    public MavenProjectMetrics getMpm() {
//        return mpm;
//    }
//
//    public void setMpm(MavenProjectMetrics mpm) {
//        this.mpm = mpm;
//    }

    @Override
    public String toString() {
        return "MavenPom{" +
                "model=" + model +
                ", file=" + file +
                ", modules=" + modules +
//                ", mpm=" + mpm +
                '}';
    }
}