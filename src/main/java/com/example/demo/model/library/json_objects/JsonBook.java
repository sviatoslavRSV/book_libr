package com.example.demo.model.library.json_objects;


import com.example.demo.model.library.Book;
import com.example.demo.model.library.json_content.Files;
import com.example.demo.model.library.json_content.Upload;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class JsonBook {
    private List<Book> data;
    private Files files;
    @JsonProperty("upload")
    private Upload uploads;

    public JsonBook() {
        this.data = new ArrayList<>();
        this.files = new Files();
    }

    public List<Book> getData() {
        return data;
    }

    public void setData(List<Book> data) {
        this.data = data;
    }

    public Files getFiles() {
        return files;
    }

    public void setFiles(Files files) {
        this.files = files;
    }

    public Upload getUploads() {
        return uploads;
    }

    public void setUploads(Upload uploads) {
        this.uploads = uploads;
    }

    @Override
    public String toString() {
        return "JsonImage{" +
                "data=" + data +
                ", files=" + files +
                ", uploads=" + uploads +
                '}';
    }
}
