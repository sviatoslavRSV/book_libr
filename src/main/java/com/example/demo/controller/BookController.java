package com.example.demo.controller;

import com.example.demo.model.library.Book;
import com.example.demo.model.library.ImageFile;
import com.example.demo.model.library.json_content.Files;
import com.example.demo.model.library.json_objects.JsonBook;
import com.example.demo.model.library.json_objects.JsonImageFile;
import com.example.demo.service.BookFileService;
import com.example.demo.service.BookService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.Path;
import java.util.*;


@Controller
public class BookController {
    private Logger logger = Logger.getLogger(BookController.class);

    @Autowired
    private BookService bookService;
    @Autowired
    private BookFileService bookFileService;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(value = "/user/books")
    @ResponseBody
    public ResponseEntity<?> getAllBooks() {
        logger.warn("in method getAllBooks");
        JsonBook data = createImageJsonFromList();
        logger.warn("send all books as responseBody");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping(value = "/admin/books/add")
    @ResponseBody
    public ResponseEntity<?> addBook(@RequestBody Book newBook) {
        logger.warn(newBook);
        if (newBook.getId() == 0) {
            bookService.addBook(newBook);
            logger.warn("book add successfully");
        } else {
            bookService.updateBook(newBook);
            logger.warn("book update successfully");
        }
        JsonBook jsonBooks = new JsonBook();
//        newBook.setImage(newBook.getImage().replaceFirst("[.][^.]+$", ""));
        newBook.setImage(newBook.getImage());
        jsonBooks.setData(new ArrayList<>(Arrays.asList(newBook)));
        return new ResponseEntity<>(jsonBooks, HttpStatus.OK);
    }

    @PostMapping("/admin/books/delete")
    @ResponseBody
    public ResponseEntity<?> removeBook(@RequestBody Book book) {
        bookService.removeBook(book);
        ImageFile imageFile = bookFileService.getImage(Integer.parseInt(book.getImage()));
        if (imageFile != null) {
            bookFileService.removeImage(imageFile.getId());
            Path path = java.nio.file.Paths.get(com.example.demo.utils.ExtensionsAndPaths.SYSTEM_PATH.replaceFirst("/", ""));
            deleteFileByPrefix(path, imageFile.getImageName());
        }
        imageFile = bookFileService.getImage(Integer.parseInt(book.getBook()));
        if (!book.getBook().equals("")) {
            bookFileService.removeImage(imageFile.getId());
            Path path = java.nio.file.Paths.get(com.example.demo.utils.ExtensionsAndPaths.SYSTEM_PATH.replaceFirst("/", ""));
            deleteFileByPrefix(path, imageFile.getImageName());
        }
        logger.warn("book id= " + book.getId() + " " + "has deleted successfully");
        return new ResponseEntity<>(new JsonBook(), HttpStatus.OK);
    }

    private JsonBook createImageJsonFromList() {
        JsonBook data = new JsonBook();
        List<Book> list = bookService.listBooks();
        data.setData(list);
        Files myFile1 = new Files();

        Map<String, JsonImageFile> imageFileMap = new HashMap<>();
        ImageFile newImage;
        for (Book book : list) {
            if (!book.getImage().equals("")) {
                newImage = bookFileService.getImage(Integer.parseInt((book.getImage())));

                JsonImageFile jsonImageFile = new JsonImageFile();
                jsonImageFile.setId(String.valueOf(newImage.getId()));
                jsonImageFile.setSystemPath(newImage.getSystemPath());
                jsonImageFile.setWebPath(newImage.getWebPath());
                jsonImageFile.setImageName(newImage.getImageName());
                jsonImageFile.setFileSize(newImage.getFileSize());

                imageFileMap.put(jsonImageFile.getId(), jsonImageFile);
            }
            if (!book.getBook().equals("")) {
                newImage = bookFileService.getImage(Integer.parseInt((book.getBook())));

                JsonImageFile jsonImageFile2 = new JsonImageFile();
                jsonImageFile2.setId(String.valueOf(newImage.getId()));
                jsonImageFile2.setSystemPath(newImage.getSystemPath());
                jsonImageFile2.setWebPath(newImage.getWebPath());
                jsonImageFile2.setImageName(newImage.getImageName());
                jsonImageFile2.setFileSize(newImage.getFileSize());

                imageFileMap.put(jsonImageFile2.getId(), jsonImageFile2);
            }
        }
        myFile1.setFiles(imageFileMap);
        data.setFiles(myFile1);
        return data;
    }

    private void deleteFileByPrefix(final Path path, final String prefix) {
        try {
            java.nio.file.Files.list(path).filter(p -> p.toString().contains(prefix)).forEach((p) -> {
                try {
                    java.nio.file.Files.deleteIfExists(p);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
















