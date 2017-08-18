package com.example.demo.repository;


import com.example.demo.model.library.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
//    Book findFirstByOrderByIdDesc();

    Book findById(int id);
}
