package com.example.demo.repository;


import com.example.demo.model.login.Userr;
import com.example.demo.model.login.token.PasswResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswResetTokenRepository extends JpaRepository<PasswResetToken, Integer> {
    PasswResetToken findByToken(String token);

    PasswResetToken findByUser(Userr userr);
}
