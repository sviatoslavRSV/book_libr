package com.example.demo.model.library.json_objects;


import com.example.demo.model.library.Book;
import com.example.demo.model.library.json_content.Message;

import java.util.ArrayList;
import java.util.List;


public class JsonMessage {
    private List<Book> data;
    private List<Message> fieldErrors = new ArrayList<>();

    public JsonMessage(String name, String status) {
        data = new ArrayList<>();
        fieldErrors.add(new Message(name, status));
    }

    public JsonMessage() {
        data = new ArrayList<>();
        fieldErrors.add(new Message());
    }

    public List<Book> getData() {
        return data;
    }

    public void setData(List<Book> data) {
        this.data = data;
    }

    public List<Message> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<Message> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
