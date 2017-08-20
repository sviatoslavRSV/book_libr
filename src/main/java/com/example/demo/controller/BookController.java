package com.example.demo.controller;

import com.example.demo.model.library.Book;
import com.example.demo.model.library.ImageFile;
import com.example.demo.model.library.json_content.Files;
import com.example.demo.model.library.json_objects.JsonBook;
import com.example.demo.model.library.json_objects.JsonImageFile;
import com.example.demo.model.library.json_objects.JsonMessage;
import com.example.demo.service.BookFileService;
import com.example.demo.service.BookService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.util.*;


@Controller
public class BookController {
    private Logger logger = Logger.getLogger(BookController.class);

    @Autowired
    private BookService bookService;
    @Autowired
    private BookFileService bookFileService;
    @Autowired
    private MessageSource messageSource;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(value = "/user/books")
    @ResponseBody
    public ResponseEntity<?> getAllBooks() {
        logger.warn("in method getAllBooks");
        JsonBook data = createJsonBookFromList();
        logger.warn("send all books as responseBody");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping(value = "/admin/books/add")
    @ResponseBody
    public ResponseEntity<?> addBook(@RequestBody Book newBook, HttpServletRequest request) {
        logger.warn(newBook);
        JsonMessage jsonMessage = checkNewBook(newBook, request);
        if (jsonMessage.getFieldErrors().get(0).getName() != null)
            return new ResponseEntity<>(jsonMessage, HttpStatus.OK);

        bookService.addBook(newBook);
        JsonBook jsonBooks = new JsonBook();
        jsonBooks.setData(new ArrayList<>(Arrays.asList(newBook)));
        return new ResponseEntity<>(jsonBooks, HttpStatus.OK);
    }

    @PostMapping("/admin/books/delete")
    @ResponseBody
    public ResponseEntity<?> removeBook(@RequestBody Book book) {
        bookService.removeBook(book);

        ImageFile imageFile = bookFileService.getImage(Integer.parseInt(book.getImage()));
        bookFileService.removeImage(imageFile.getId());
        Path path = java.nio.file.Paths.get(com.example.demo.utils.ExtensionsAndPaths.SYSTEM_PATH.replaceFirst("/", ""));
        deleteFileByPrefix(path, imageFile.getImageName());

        imageFile = bookFileService.getImage(Integer.parseInt(book.getBook()));
        bookFileService.removeImage(imageFile.getId());
        path = java.nio.file.Paths.get(com.example.demo.utils.ExtensionsAndPaths.SYSTEM_PATH.replaceFirst("/", ""));
        deleteFileByPrefix(path, imageFile.getImageName());

        return new ResponseEntity<>(new JsonBook(), HttpStatus.OK);
    }

    private JsonMessage checkNewBook(Book newBook, HttpServletRequest request) {
        if (newBook.getImage() == "") {
            String message = messageSource.getMessage("image.content.err", null, request.getLocale());
            return new JsonMessage("image", message);
        }
        if (newBook.getTitle() == "") {
            String message = messageSource.getMessage("title.content.err", null, request.getLocale());
            return new JsonMessage("title", message);
        }
        if (newBook.getBook() == "") {
            String message = messageSource.getMessage("book.content.err", null, request.getLocale());
            return new JsonMessage("book", message);
        }
        if (newBook.getTitle().length() > 255) {
            String message = messageSource.getMessage("title.content.err", null, request.getLocale());
            return new JsonMessage("title", message);
        }
        if (newBook.getAuthor().length() > 255) {
            String message = messageSource.getMessage("author.content.err", null, request.getLocale());
            return new JsonMessage("author", message);
        }
        if (newBook.getPublishOffice().length() > 255) {
            String message = messageSource.getMessage("publOffice.content.err", null, request.getLocale());
            return new JsonMessage("publishOffice", message);
        }
        if (newBook.getDescription().length() > 800) {
            String message = messageSource.getMessage("descript.content.err", null, request.getLocale());
            return new JsonMessage("description", message);
        }
        return new JsonMessage();
    }

    private JsonBook createJsonBookFromList() {
        JsonBook jsonBook = new JsonBook();
        List<Book> list = bookService.listBooks();
        jsonBook.setData(list);

        Map<String, JsonImageFile> imageFileMap = new HashMap<>();
        ImageFile newImage;
        for (Book book : list) {
            newImage = bookFileService.getImage(Integer.parseInt((book.getImage())));
            JsonImageFile jsonImageFile = new JsonImageFile(newImage);
            imageFileMap.put(jsonImageFile.getId(), jsonImageFile);
            newImage = bookFileService.getImage(Integer.parseInt((book.getBook())));
            JsonImageFile jsonImageFile2 = new JsonImageFile(newImage);
            imageFileMap.put(jsonImageFile2.getId(), jsonImageFile2);
        }
        Files files = new Files();
        files.setFiles(imageFileMap);
        jsonBook.setFiles(files);
        return jsonBook;
    }

    private void deleteFileByPrefix(final Path path, final String prefix) {
        try {
            java.nio.file.Files.list(path).filter(p -> p.toString().contains(prefix)).forEach((p) -> {
                try {
                    java.nio.file.Files.deleteIfExists(p);
                } catch (Exception e) {
                    logger.warn("file: " + prefix + " is not exist");
                }
            });
        } catch (Exception e) {
            logger.warn("no path: " + path.toString());
        }
    }
}
















