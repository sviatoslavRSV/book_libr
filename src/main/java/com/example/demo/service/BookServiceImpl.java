package com.example.demo.service;

import com.example.demo.model.library.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void addBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void removeBook(Book book) {
        bookRepository.delete(book);
    }

    /*@Override
    public Book getBookById(int id) {
        return bookRepository.findOne((long) id);
    }

    @Override
    public Book getBookByName(String name) {
        return bookRepository.findByName(name);
    }*/

//    @Override
//    public Book getLastRecord() {
//        return bookRepository.findFirstByOrderByIdDesc();
//    }

    @Override
    public void updateBook(Book book) {
        Book newBook = bookRepository.findById(book.getId());
        newBook.setTitle(book.getTitle());
        newBook.setAuthor(book.getAuthor());
        newBook.setDescription(book.getDescription());
//        newBook.setImage(book.getImage());
        bookRepository.save(newBook);
    }

    @Override
    public List<Book> listBooks() {
        return bookRepository.findAll();
    }
}
