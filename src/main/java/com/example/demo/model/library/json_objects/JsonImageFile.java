package com.example.demo.model.library.json_objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonImageFile {
    private String id;
    @JsonProperty("filesize")
    private String fileSize;
    @JsonProperty("web_path")
    private String webPath;
    @JsonProperty("system_path")
    private String systemPath;
    @JsonProperty("filename")
    private String imageName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getWebPath() {
        return webPath;
    }

    public void setWebPath(String webPath) {
        this.webPath = webPath;
    }

    public String getSystemPath() {
        return systemPath;
    }

    public void setSystemPath(String systemPath) {
        this.systemPath = systemPath;
    }

    @Override
    public String toString() {
        return "ImageFile{" +
                "id=" + id +
                ", fileSize='" + fileSize + '\'' +
                ", webPath='" + webPath + '\'' +
                ", systemPath='" + systemPath + '\'' +
                ", imageName='" + imageName + '\'' +
                '}';
    }
}
