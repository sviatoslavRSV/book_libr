package com.example.demo.service;


import com.example.demo.model.login.Role;
import com.example.demo.model.login.Userr;
import com.example.demo.model.login.password.PasswordDTO;
import com.example.demo.model.login.token.PasswResetToken;
import com.example.demo.model.login.token.VerifToken;
import com.example.demo.repository.PasswResetTokenRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.TokenRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private PasswResetTokenRepository resetTokenRepository;
    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @Override
    public Userr findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Userr findUserByToken(String token) {
        return tokenRepository.findByToken(token).getUser();
    }

    @Override
    public void createUserAcount(Userr user) {
        Role role = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<>(Arrays.asList(role)));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void saveRegisteredUser(Userr user) {
        userRepository.save(user);
    }

    @Override
    public void createToken(Userr user, String token) {
        VerifToken verifToken = new VerifToken(user, token);
        tokenRepository.save(verifToken);
    }

    @Override
    public void createPasswResetToken(Userr userr, String token) {
        PasswResetToken passwResetToken = resetTokenRepository.findByUser(userr);
        if (passwResetToken != null) {
            resetTokenRepository.delete(passwResetToken);
        }
        passwResetToken = new PasswResetToken(userr, token);
        resetTokenRepository.save(passwResetToken);
    }

    @Override
    public void changeUserPassword(Userr userr, PasswordDTO password) {
        userr.setPassword(bCryptPasswordEncoder.encode(password.getPassword()));
        userRepository.save(userr);
    }

    @Override
    public void deleteAcount(Userr userr) {
        VerifToken verifToken = tokenRepository.findByUser(userr);
        if (verifToken != null) {
            tokenRepository.delete(verifToken);
        }
        PasswResetToken passwResetToken = resetTokenRepository.findByUser(userr);
        if (passwResetToken != null) {
            resetTokenRepository.delete(passwResetToken);
        }
        userRepository.delete(userr);
    }

    @Override
    public VerifToken getVerifToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public PasswResetToken getPasswResetToken(String token) {
        return resetTokenRepository.findByToken(token);
    }

    @Override
    public boolean checkIfPasswordMatches(Userr userr, String password) {
        return bCryptPasswordEncoder.matches(password, userr.getPassword());
    }

    @Override
    public VerifToken createNewToken(String existToken) {
        VerifToken newToken = tokenRepository.findByToken(existToken);
        newToken.updateToken(UUID.randomUUID().toString());
        newToken = tokenRepository.save(newToken);
        return newToken;
    }
}
