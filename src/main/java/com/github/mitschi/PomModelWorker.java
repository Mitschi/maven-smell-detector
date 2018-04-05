package com.github.mitschi;

import org.apache.commons.io.FileUtils;
import org.apache.maven.pom._4_0.Model;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;


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

            String pom = FileUtils.readFileToString(pomFile);

            pom = pom.replaceAll("<.*:.*/>", "");

            InputStream is = new ByteArrayInputStream(pom.getBytes("UTF-8"));

            JAXBContext jc = JAXBContext.newInstance(Model.class);

            Unmarshaller unmarshaller = jc.createUnmarshaller();
            JAXBElement<Model> feed = unmarshaller.unmarshal(new StreamSource(is), Model.class);
            return feed.getValue();
        }catch(Exception e) { //currently we do nothing!
                e.printStackTrace();
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
