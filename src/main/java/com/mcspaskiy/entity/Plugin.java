package com.mcspaskiy.entity;

import java.util.ArrayList;
import java.util.List;

public class Plugin {
    private String artifactID;
    private String version;
    private Configuration configuration;
    private List<Execution> executions;

    public Plugin() {
        configuration = new Configuration();
        executions = new ArrayList<>();
    }

    public void setArtifactID(String value) {
        this.artifactID = value;
    }

    public void setVersion(String value) {
        this.version = value;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public List<Execution> getExecutions() {
        return executions;
    }
}