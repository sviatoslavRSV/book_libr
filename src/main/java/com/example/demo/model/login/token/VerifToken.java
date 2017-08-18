package com.example.demo.model.login.token;


import com.example.demo.model.login.Userr;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "verif_token")
public class VerifToken {
    private static final int DURATION = 1;     //min
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "token_id")
    private int id;
    //    @Column(name = "token")
    private String token;
    @OneToOne(targetEntity = Userr.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "u_id")
    private Userr user;
    @Column(name = "expire_time")
    private Date endOfTime;

    public VerifToken() { }

    public VerifToken(Userr user, String token) {
        this.token = token;
        this.user = user;
        this.endOfTime = calculateEndOfTime(DURATION);
    }

    private Date calculateEndOfTime(final int expiryTimeInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(calendar.getTime().getTime());
    }

    public void updateToken(String token) {
        this.token = token;
        this.endOfTime = calculateEndOfTime(DURATION);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Userr getUser() {
        return user;
    }

    public void setUser(Userr user) {
        this.user = user;
    }

    public Date getEndOfTime() {
        return endOfTime;
    }

    public void setEndOfTime(Date endOfTime) {
        this.endOfTime = endOfTime;
    }
}
