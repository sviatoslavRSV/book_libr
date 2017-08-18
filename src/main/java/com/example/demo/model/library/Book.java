package com.example.demo.model.library;

import javax.persistence.*;

//@JsonAutoDetect
@Entity
@Table(name = "books")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "book_title")
//    @Size(min = 1, max = 20)
    private String title;

    @Column(name = "book_author")
//    @Size(min = 1, max = 20)
    private String author;

    @Column(name = "publish_office")
//    @Size(min = 1, max = 20)
    private String publishOffice;

    @Column(name = "short_descript")
//    @Size(min = 1, max = 255)
    private String description;

//    @Column(name = "image")
    private String image;

    @Column(name = "book_file")
    private String book;

    public Book() {

    }

    public Book(String title, String author, String publishOffice, String description) {
        this.title = title;
        this.author = author;
        this.publishOffice = publishOffice;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishOffice() {
        return publishOffice;
    }

    public void setPublishOffice(String publishOffice) {
        this.publishOffice = publishOffice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publishOffice='" + publishOffice + '\'' +
                ", description='" + description + '\'' +
//                ", image='" + image + '\'' +
                '}';
    }
}
