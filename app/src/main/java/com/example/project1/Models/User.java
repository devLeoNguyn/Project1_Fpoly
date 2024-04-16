package com.example.project1.Models;

public class User {

    String name;
    String pass;
    String email;
    public User(String name, String pass, String email) {
        this.name = name;
        this.pass = pass;
        this.email = email;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    public User(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }
    public User() {

    }

}
