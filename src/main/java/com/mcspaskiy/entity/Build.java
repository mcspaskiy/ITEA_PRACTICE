package com.mcspaskiy.entity;

import java.util.ArrayList;
import java.util.List;

public class Build {
    private List<Plugin> plugins;

    public Build() {
        plugins = new ArrayList<>();
    }

    public List<Plugin> getPlugins() {
        return plugins;
    }
}