package com.example.demo.repository;


import com.example.demo.model.login.Userr;
import com.example.demo.model.login.token.VerifToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<VerifToken, Long> {
    VerifToken findByToken(String token);

    VerifToken findByUser(Userr user);
}
