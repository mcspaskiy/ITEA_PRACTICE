package com.mcspaskiy.entity;

import java.util.ArrayList;
import java.util.List;

public class Execution {
    private String id;
    private String phase;
    private List<String> goals;

    public Execution() {
        goals = new ArrayList<>();
    }

    public void setID(String value) {
        this.id = value;
    }

    public void setPhase(String value) {
        this.phase = value;
    }

    public List<String> getGoals() {
        return goals;
    }
}
