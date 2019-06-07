package ru.cg.webbpm.repository.model.nexus;

import java.util.List;

public class NexusPackage {
    private String id;
    private String repository;
    private String format;
    private String group;
    private String name;
    private String version;
    private List<NexusAsset> assets;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<NexusAsset> getAssets() {
        return assets;
    }

    public void setAssets(List<NexusAsset> assets) {
        this.assets = assets;
    }

}
