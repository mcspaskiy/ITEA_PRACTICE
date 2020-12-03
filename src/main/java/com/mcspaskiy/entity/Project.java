package com.mcspaskiy.entity;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private String modelVersion;
    private String groupID;
    private String artifactID;
    private String version;
    private String name;
    private String url;
    private Properties properties;
    private List<Dependency> dependencies;
    private Build build;
    private String xmlnsAttr;
    private String xmlnsXsiAttr;
    private String xsiSchemaLocationAttr;

    public Project() {
        properties = new Properties();
        dependencies = new ArrayList<>();
        build = new Build();
        dependencies = new ArrayList<>();
    }

    public void setModelVersion(String value) {
        this.modelVersion = value;
    }

    public void setGroupID(String value) {
        this.groupID = value;
    }

    public void setArtifactID(String value) {
        this.artifactID = value;
    }

    public void setVersion(String value) {
        this.version = value;
    }

    public void setName(String value) {
        this.name = value;
    }

    public void setURL(String value) {
        this.url = value;
    }

    public Properties getProperties() {
        return properties;
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }

    public Build getBuild() {
        return build;
    }

    public void setXmlnsAttr(String xmlnsAttr) {
        this.xmlnsAttr = xmlnsAttr;
    }

    public void setXmlnsXsiAttr(String xmlnsXsiAttr) {
        this.xmlnsXsiAttr = xmlnsXsiAttr;
    }

    public void setXsiSchemaLocationAttr(String xsiSchemaLocationAttr) {
        this.xsiSchemaLocationAttr = xsiSchemaLocationAttr;
    }

    @Override
    public String toString() {
        return "Project{" +
                "modelVersion='" + modelVersion + '\'' +
                ", groupID='" + groupID + '\'' +
                ", artifactID='" + artifactID + '\'' +
                ", version='" + version + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", properties=" + properties +
                ", dependencies=" + dependencies +
                ", build=" + build +
                ", xmlnsAttr='" + xmlnsAttr + '\'' +
                ", xmlnsXsiAttr='" + xmlnsXsiAttr + '\'' +
                ", xsiSchemaLocationAttr='" + xsiSchemaLocationAttr + '\'' +
                '}';
    }
}
