package com.example.demo.model.library;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @Column(name = "b_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "book_title")
    private String title;
    @Column(name = "book_author")
    private String author;
    @Column(name = "publish_office")
    private String publishOffice;
    @Type(type = "text")
    @Column(name = "short_descript")
    private String description;
    @Column(name = "image")
    private String image;
    @Column(name = "book_file")
    private String book;
    @JsonIgnore
    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Comment> comments;

    public Book() {

    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
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
                ", image='" + image + '\'' +
                '}';
    }
}
