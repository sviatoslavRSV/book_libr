package com.example.demo.service;

import com.example.demo.model.library.Book;
import com.example.demo.model.library.Comment;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BookRepository bookRepository;

    @Override
    public void addBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void removeBook(Book book) {
        for (Comment c : commentRepository.findAll()) {
            commentRepository.delete(c);
        }
        bookRepository.delete(book);
    }

    @Override
    public Book getBookById(int id) {
        return bookRepository.findOne(id);
    }

    @Override
    public void updateBook(Book book) {
        Book newBook = bookRepository.findOne(book.getId());
        newBook.setTitle(book.getTitle());
        newBook.setAuthor(book.getAuthor());
        newBook.setDescription(book.getDescription());
        newBook.setImage(book.getImage());
        newBook.setBook(book.getBook());
        newBook.setComments(book.getComments());
        newBook.setPublishOffice(book.getPublishOffice());
        bookRepository.save(newBook);
    }

    @Override
    public void saveComments(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public List<Book> listBooks() {
        return bookRepository.findAll();
    }
}
