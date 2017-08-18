package com.example.demo.repository;


import com.example.demo.model.library.BookFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookFileRepository extends JpaRepository<BookFile, Long> {
    BookFile findByFileName(String fileName);
}
