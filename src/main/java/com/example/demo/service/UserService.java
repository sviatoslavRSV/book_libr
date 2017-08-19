package com.example.demo.service;


import com.example.demo.model.login.Userr;
import com.example.demo.model.login.password.PasswordDTO;
import com.example.demo.model.login.token.PasswResetToken;
import com.example.demo.model.login.token.VerifToken;

public interface UserService {
    Userr findUserByEmail(String email);

    Userr findUserByToken(String token);

    void createUserAcount(Userr user);

    void createAdminAcount(Userr user);

    void saveRegisteredUser(Userr user);

    void createToken(Userr user, String token);

    void createPasswResetToken(Userr userr, String token);

    VerifToken getVerifToken(String token);

    VerifToken createNewToken(String token);

    PasswResetToken getPasswResetToken(String token);

    void changeUserPassword(Userr userr, PasswordDTO password);

    boolean checkIfPasswordMatches(Userr userr, String password);

    void deleteAcount(Userr userr);
}
