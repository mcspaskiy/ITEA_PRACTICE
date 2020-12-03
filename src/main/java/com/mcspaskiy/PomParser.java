package com.mcspaskiy;

import com.mcspaskiy.entity.*;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;

public class PomParser extends DefaultHandler {
    private Project project;
    private String currentNode;
    private String parentNode;

    public PomParser() {
        project = new Project();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (currentNode != null) {
            parentNode = currentNode;
        }
        currentNode = qName;
        parseAttributes(qName, attributes);
    }

    private void parseAttributes(String qName, Attributes attributes) {
        if ("project".equals(qName)) {
            for (int i = 0; i < attributes.getLength(); i++) {
                String attrName = attributes.getQName(i);
                switch (attrName) {
                    case "xmlns":
                        project.setXmlnsAttr(attributes.getValue(i));
                        break;
                    case "xmlns:xsi":
                        project.setXmlnsXsiAttr(attributes.getValue(i));
                        break;
                    case "xsi:schemaLocation":
                        project.setXsiSchemaLocationAttr(attributes.getValue(i));
                        break;
                }
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String value = "";
        for (int i = start; i < (start + length); i++) {
            value += ch[i];
        }
        value = value.trim().replaceAll("\n", "");
        if (value.length() > 0) {
            {
                switch (parentNode) {
                    case "project":
                        parseProjectNodes(value);
                        break;

                    case "properties":
                        parsePropsNodes(value);
                        break;

                    case "dependency":
                        parseDependencies(value);
                        break;

                    case "plugin":
                        parsePlugins(value);
                        break;

                    case "descriptorRefs":
                        parseDescriptorRefs(value);
                        break;

                    case "manifest":
                        parseManifest(value);
                        break;

                    case "execution":
                        parseExecutions(value);
                        break;

                    case "goals":
                        parseGoalsNode(value);
                        break;
                }
            }
        }
    }

    private void parseGoalsNode(String value) {
        List<Plugin> plugins = project.getBuild().getPlugins();
        Plugin plugin = plugins.get(plugins.size() - 1);
        List<Execution> executions = plugin.getExecutions();
        Execution execution = executions.get(executions.size() - 1);
        List<String> goals = execution.getGoals();

        switch (currentNode) {
            case "goal": {
                goals.add(value);
                break;
            }
        }
    }

    private void parseExecutions(String value) {
        List<Plugin> plugins = project.getBuild().getPlugins();
        Plugin plugin = plugins.get(plugins.size() - 1);
        List<Execution> executions = plugin.getExecutions();
        switch (currentNode) {
            case "id":
                Execution execution = new Execution();
                executions.add(execution);
                execution.setID(value);
                break;
            case "phase":
                execution = executions.get(executions.size() - 1);
                execution.setPhase(value);
                break;
        }
    }

    private void parseManifest(String value) {
        List<Plugin> plugins = project.getBuild().getPlugins();
        Plugin plugin = plugins.get(plugins.size() - 1);
        Configuration configuration = plugin.getConfiguration();
        Archive archive = configuration.getArchive();
        Manifest manifest = archive.getManifest();
        switch (currentNode) {
            case "mainClass":
                manifest.setMainClass(value);
                break;
        }
    }

    private void parseDescriptorRefs(String value) {
        List<Plugin> plugins = project.getBuild().getPlugins();
        Plugin plugin = plugins.get(plugins.size() - 1);
        List<String> descriptorRefs = plugin.getConfiguration().getDescriptorRefs();
        switch (currentNode) {
            case "descriptorRef":
                descriptorRefs.add(value);
                break;
        }
    }

    private void parsePlugins(String value) {
        List<Plugin> plugins = project.getBuild().getPlugins();
        if (plugins.isEmpty()) {
            plugins.add(new Plugin());
        }
        Plugin plugin = plugins.get(plugins.size() - 1);
        switch (currentNode) {
            case "artifactId":
                plugin.setArtifactID(value);
                break;
            case "version":
                plugin.setVersion(value);
                break;
        }
    }

    private void parseDependencies(String value) {
        List<Dependency> dependencies = project.getDependencies();
        switch (currentNode) {
            case "groupId":
                dependencies.add(new Dependency());
                dependencies.get(dependencies.size() - 1).setGroupID(value);
                break;
            case "artifactId":
                dependencies.get(dependencies.size() - 1).setArtifactID(value);
                break;
            case "version":
                dependencies.get(dependencies.size() - 1).setVersion(value);
                break;
            case "scope":
                dependencies.get(dependencies.size() - 1).setScope(value);
                break;
        }
    }

    private void parsePropsNodes(String value) {
        Properties properties = project.getProperties();
        switch (currentNode) {
            case "project.build.sourceEncoding":
                properties.setProjectBuildSourceEncoding(value);
                break;
            case "maven.compiler.source":
                properties.setMavenCompilerSource(value);
                break;
            case "maven.compiler.target":
                properties.setMavenCompilerTarget(value);
                break;
        }
    }

    private void parseProjectNodes(String value) {
        switch (currentNode) {
            case "modelVersion":
                project.setModelVersion(value);
                break;
            case "groupId":
                project.setGroupID(value);
                break;
            case "artifactId":
                project.setArtifactID(value);
                break;
            case "version":
                project.setVersion(value);
                break;
            case "name":
                project.setName(value);
                break;
            case "url":
                project.setURL(value);
                break;
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        currentNode = null;
    }

    @Override
    public void startDocument() {
        System.out.println("Started doc parsing");
    }

    @Override
    public void endDocument() {
        System.out.println(project.toString());
        System.out.println("Finished doc parsing");
    }
}
