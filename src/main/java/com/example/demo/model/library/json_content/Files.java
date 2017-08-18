package com.example.demo.model.library.json_content;


import com.example.demo.model.library.json_objects.JsonImageFile;

import java.util.HashMap;
import java.util.Map;

public class Files {
    private Map<String, JsonImageFile> files;

    public Files() {
        this.files = new HashMap<>();
    }

    public Map<String, JsonImageFile> getFiles() {
        return files;
    }

    public void setFiles(Map<String, JsonImageFile> files) {
        this.files = files;
    }
}
