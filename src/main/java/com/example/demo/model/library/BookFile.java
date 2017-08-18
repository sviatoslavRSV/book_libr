package com.example.demo.model.library;


import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "book_file")
public class BookFile {
    @Id
    @Column(name = "f_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "file_name")
    private byte[] fileName;

//    @OneToOne(targetEntity = Book.class, fetch = FetchType.EAGER)
//    @JoinColumn(nullable = false, name = "id")
//    private Book book;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getFileName() {
        return fileName;
    }

    public void setFileName(byte[] fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "BookFile{" +
                "id=" + id +
                ", fileName=" + Arrays.toString(fileName) +
                '}';
    }
}
