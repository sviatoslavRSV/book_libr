package com.example.demo;

import com.example.demo.model.library.Book;
import com.example.demo.model.library.ImageFile;
import com.example.demo.model.login.Role;
import com.example.demo.model.login.Userr;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.BookFileService;
import com.example.demo.service.BookService;
import com.example.demo.service.UserService;
import com.example.demo.utils.ExtensionsAndPaths;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.*;

@SpringBootApplication
public class BooksApplication {
    private final Logger logger = Logger.getLogger(BooksApplication.class);

    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    private BookFileService bookFileService;

    public static void main(String[] args) {
        SpringApplication.run(BooksApplication.class, args);
//		SpringApplication.run(TaskForHmApplication.class, "--debug");
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext context) {
        return strings -> {
    /*add image extensions*/
            ExtensionsAndPaths.setExtensionsImageSet(new HashSet<>(Arrays.asList("png", "jpg", "bmp", "jpeg")));
    /*add book extensions*/
            ExtensionsAndPaths.setExtensionsBookSet(new HashSet<>(Arrays.asList("txt", "rtf", "doc", "odt", "pdf")));
            Role role = roleRepository.findByRole("ADMIN");
            if (role == null) {
                roleRepository.save(new Role("ADMIN"));
            }
            role = roleRepository.findByRole("USER");
            if (role == null) {
                roleRepository.save(new Role("USER"));
            }

            Userr userAdmin = userService.findUserByEmail("admin@gmail.com");
            if (userAdmin == null) {
                userAdmin = new Userr();
                userAdmin.setName("Admin");
                userAdmin.setLastName("Admin");
                userAdmin.setEmail("admin@gmail.com");
                userAdmin.setPassword("admin");
                userAdmin.setActive(true);
                userService.createAdminAcount(userAdmin);
                String token = UUID.randomUUID().toString();
                userService.createToken(userAdmin, token);
            }
            createList();
        };
    }

    private void createList() {
        List<String> titleList = Arrays.asList("Macbeth", "Picasso", "On the sublime", "Naturalis historia");
        List<String> authorList = Arrays.asList("William Shakespeare", "Pablo Picasso", "Longinus", "Pliny the Elder");
        List<String> publOffList = Arrays.asList("Last edited by ImportBot\n" +
                "June 24, 2017", "Last edited by Darby\n" +
                "January 2, 2017", "Last edited by Anand Chitipothu\n" +
                "November 23, 2012", "", "", "", "", "", "", "");
        List<String> descriptionList = Arrays.asList("The play concerns a trusted " +
                        "general who secretly lusts for power. Encouraged by the prophecies of three witches and urged " +
                        "on by his ambitious wife Macbeth commits regicide. Left fearful and superstitious by this desperate " +
                        "act he is driven to a spiralling course of murder and outrage, almost inevitably culminating in " +
                        "his own death. One of Shakespeare’s most popular tragedies, Macbeth is ostensibly based on the " +
                        "Scottish king although the story represented in the play bears no relation to historical fact as " +
                        "the true King Macbeth was well respected by his contemporaries",
                "Macbeth commits regicide. Left fearful and superstitious by this desperate " +
                        "act he is driven to a spiralling course of murder and outrage, almost inevitably culminating in " +
                        "his own death. One of Shakespeare’s most popular tragedies, Macbeth is ostensibly based on the " +
                        "Scottish king although the story represented in the play bears",
                " Encouraged by the prophecies of three witches and urged " +
                        "on by his ambitious wife Macbeth commits regicide. Left fearful and superstitious " +
                        "by this desperate " +
                        "act he is driven to a spiralling course of murder and outrage,",
                "Heavens to Betsy! There's no description for this book yet. Can you help?");
        List<String> imageListSP = Arrays.asList("/src/main/resources/upload/800x600 (6).jpg",
                "/src/main/resources/upload/800x600 (7).jpg",
                "/src/main/resources/upload/download (5).jpg",
                "/src/main/resources/upload/mackbeth.jpg");
        List<String> bookFileListSP = Arrays.asList("/src/main/resources/upload/java8book-SHILDT.pdf",
                "/src/main/resources/upload/JSP Practical Book.pdf",
                "/src/main/resources/upload/Thinking_in_Java_4.pdf",
                "/src/main/resources/upload/Effective.doc");
        List<String> imageListWP = Arrays.asList("/user/download/800x600 (6).jpg",
                "/user/download/800x600 (7).jpg",
                "/user/download/download (5).jpg",
                "/user/download/mackbeth.jpg");
        List<String> bookFileListWP = Arrays.asList("/user/download/java8book-SHILDT.pdf",
                "/user/download/JSP Practical Book.pdf",
                "/user/download/Thinking_in_Java_4.pdf",
                "/user/download/Effective.doc");
        List<String> bookFileNameList = Arrays.asList("java8book-SHILDT.pdf",
                "JSP Practical Book.pdf",
                "Thinking_in_Java_4.pdf",
                "Effective.doc");
        List<String> imageFileNameList = Arrays.asList("800x600 (6).jpg",
                "800x600 (7).jpg",
                "download (5).jpg",
                "mackbeth.jpg");
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            logger.warn(i);
            int num = random.nextInt(4);
            ImageFile newImage = new ImageFile();
            newImage.setImageName(imageFileNameList.get(num));
            newImage.setFileSize("19100100");
            newImage.setSystemPath(imageListSP.get(num));
            newImage.setWebPath(imageListWP.get(num));
            newImage = bookFileService.saveImage(newImage);

            ImageFile newBook = new ImageFile();
            newBook.setImageName(bookFileNameList.get(num));
            newBook.setFileSize("9176");
            newBook.setSystemPath(bookFileListSP.get(num));
            newBook.setWebPath(bookFileListWP.get(num));
            newBook = bookFileService.saveImage(newBook);

            Book book = new Book();
            book.setTitle(titleList.get(num) + i);
            book.setAuthor(authorList.get(num) + i);
            book.setPublishOffice(publOffList.get(num) + i);
            book.setDescription(descriptionList.get(num) + i);
            book.setImage(String.valueOf(newImage.getId()));
            book.setBook(String.valueOf(newBook.getId()));
            bookService.addBook(book);
        }
    }
}
