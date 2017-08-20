package com.example.demo.service;


import com.example.demo.model.library.Book;

import java.util.List;

public interface BookService {
    void addBook(Book book);

    void removeBook(Book book);

    Book getBookById(int id);

    List<Book> listBooks();
}
