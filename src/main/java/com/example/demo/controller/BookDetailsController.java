package com.example.demo.controller;

import com.example.demo.model.library.Book;
import com.example.demo.model.library.Comment;
import com.example.demo.model.library.ImageFile;
import com.example.demo.service.BookFileService;
import com.example.demo.service.BookService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BookDetailsController {
    private Logger logger = Logger.getLogger(BookController.class);

    @Autowired
    private BookFileService bookFileService;
    @Autowired
    private BookService bookService;


    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(value = "/user/book/{id}")
    public ModelAndView showCommentPage(@PathVariable int id) {
        logger.warn("show comment page");
        Book book = bookService.getBookById(id);
        ModelAndView modelAndView = new ModelAndView("user/book-details", "book", book);
        ImageFile image = bookFileService.getImage(Integer.parseInt(book.getImage()));
        ImageFile bookFile = bookFileService.getImage(Integer.parseInt(book.getBook()));
        modelAndView.addObject("image", image);
        modelAndView.addObject("bookFile", bookFile);
        modelAndView.addObject("comments", book.getComments());
        for (Comment comment : book.getComments()) {
            logger.warn(comment.getComment());
        }
        return modelAndView;
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping(value = "/user/book/{id}")
    @ResponseBody
    public String receiveComments(@PathVariable int id, @RequestBody String comm, HttpServletRequest request) {
        Book book = bookService.getBookById(id);
//        if (book.getComments() == null) book.setComments(comm + "\n");
//        else
        Comment comment = new Comment();
        comment.setComment(comm);
        comment.setBook(book);
        book.getComments().add(comment);
        bookService.updateBook(book);
        bookService.saveComments(comment);
        logger.warn("exit from method receiveComments " + comm + " id= " + id);
        return "success";
    }
}
