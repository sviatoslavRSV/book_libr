package com.example.demo.model.library;

import javax.persistence.*;

@Entity
@Table(name = "image_file")
public class ImageFile {
    @Id
    @Column(name = "i_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String fileSize;
    private String webPath;
    private String systemPath;
//    @Column(name = "imageName", nullable = false)
    private String imageName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
