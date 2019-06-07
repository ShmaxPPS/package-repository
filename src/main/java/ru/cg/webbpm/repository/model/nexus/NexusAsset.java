package ru.cg.webbpm.repository.model.nexus;

import java.net.URL;

public class NexusAsset {
    private URL downloadUrl;
    private String path;
    private String id;
    private String repository;
    private String format;
    private NexusChecksum checksum;

    public URL getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(URL downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

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

    public NexusChecksum getChecksum() {
        return checksum;
    }

    public void setChecksum(NexusChecksum checksum) {
        this.checksum = checksum;
    }
}
