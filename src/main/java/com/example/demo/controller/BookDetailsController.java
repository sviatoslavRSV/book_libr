package com.example.demo.controller;

import com.example.demo.model.library.Book;
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
        ImageFile image = bookFileService.getImage(Integer.parseInt(book.getImage()));
        ImageFile bookFile = bookFileService.getImage(Integer.parseInt(book.getBook()));
        ModelAndView modelAndView = new ModelAndView("user/book-details", "book", book);
        modelAndView.addObject("image", image);
        modelAndView.addObject("bookFile", bookFile);
        return modelAndView;
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping(value = "/user/book/{id}")
    @ResponseBody
    public String receiveComments(@PathVariable int id, @RequestBody String comm, HttpServletRequest request) {
        Book book = bookService.getBookById(id);
        book.setComments(book.getComments() + "\n" + comm + "\n");
        bookService.updateBook(book);
        logger.warn("exit from method receiveComments " + comm + " id= " + id);
        return "success";
    }
}
