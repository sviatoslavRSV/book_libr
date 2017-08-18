package com.example.demo.service;


import com.example.demo.model.library.Book;

import java.util.List;

public interface BookService {
    void addBook(Book book);

    void updateBook(Book book);

    void removeBook(Book book);

//    Book getLastRecord();

    List<Book> listBooks();
}
