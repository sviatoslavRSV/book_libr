package com.example.demo.model.login;


import com.example.demo.validation.ValidEmail;
import com.example.demo.validation.ValidPassw;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

/*@PasswordMatch.List({
        @PasswordMatch(
                field = "password",
                fieldMatch = "passwordMatch",
                message = "password invalid"
        )
})*/
//@PasswordMatch
@Entity
@Table(name = "users")
public class Userr {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "u_id")
    private int id;

    @Column(name = "last_name")
    @Size(min = 1, max = 10)
    private String lastName;

    @Column(name = "name")
    @Size(min = 1, max = 10)
    private String name;

    @Column(name = "email")
    @ValidEmail
    private String email;

    @Column(name = "password")
    @ValidPassw
    private String password;

    @Column(name = "active")
    private boolean active;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public Userr() {
        this.active = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", lastName='" + lastName + '\'' +
                ", active=" + active +
                ", roles=" + roles +
                '}';
    }
}
