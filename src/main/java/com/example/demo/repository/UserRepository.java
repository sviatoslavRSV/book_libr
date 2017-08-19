package com.example.demo.repository;


import com.example.demo.model.login.Userr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Userr, Integer> {
    Userr findByEmail(String email);
}
