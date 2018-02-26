package com.github.mitschi;

import org.apache.maven.pom._4_0.Model;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileInputStream;


public  class PomModelWorker extends Thread{
    private File file;
    private Model model;

    public PomModelWorker(File file) {
        this.file = file;
    }

    public void run() {
        try {
            model = createPomModel(file);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    protected Model createPomModel(File pomFile) {
        try {
            JAXBContext jc = JAXBContext.newInstance(Model.class);

            Unmarshaller unmarshaller = jc.createUnmarshaller();
            JAXBElement<Model> feed = unmarshaller.unmarshal(new StreamSource(new FileInputStream(pomFile)), Model.class);
            return feed.getValue();
        }catch(Exception e) { //currently we do nothing!
//                e.printStackTrace();
        }
        return null;
    }

    public File getFile() {
        return file;
    }

    public Model getModel() {
        return model;
    }
}
