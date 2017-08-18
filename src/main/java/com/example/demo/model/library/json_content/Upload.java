package com.example.demo.model.library.json_content;


public class Upload {
    private String id;

    public Upload() {

    }

    public Upload(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Upload{" +
                "id='" + id + '\'' +
                '}';
    }
}
