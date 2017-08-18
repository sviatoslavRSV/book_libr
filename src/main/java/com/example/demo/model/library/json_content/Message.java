package com.example.demo.model.library.json_content;

public class Message {
    private String name;
    private String status;

    public Message() {

    }

    public Message(String name, String status) {
        this.name = name;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
