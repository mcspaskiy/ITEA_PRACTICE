package com.mcspaskiy.entity;

public class Dependency {
    private String groupID;
    private String artifactID;
    private String version;
    private String scope;

    public void setGroupID(String value) {
        this.groupID = value;
    }

    public void setArtifactID(String value) {
        this.artifactID = value;
    }

    public void setVersion(String value) {
        this.version = value;
    }

    public void setScope(String value) {
        this.scope = value;
    }
}
