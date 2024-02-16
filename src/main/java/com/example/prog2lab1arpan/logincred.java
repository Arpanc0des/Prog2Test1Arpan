package com.example.prog2lab1arpan;

public class logincred {
    private int id;
    private String user;
    private String password;

    public logincred(int id, String user, String password) {
        this.id = id;
        this.user = user;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
