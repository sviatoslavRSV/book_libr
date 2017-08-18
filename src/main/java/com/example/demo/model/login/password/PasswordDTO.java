package com.example.demo.model.login.password;


import com.example.demo.validation.ValidPasswDTO;

import javax.validation.constraints.Size;

@ValidPasswDTO
public class PasswordDTO {
    @Size(min = 7, max = 10)
    private String password;
    @Size(min = 7, max = 10)
    private String confirmPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
