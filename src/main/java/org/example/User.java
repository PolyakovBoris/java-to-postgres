package org.example;
public class User {

    String login;
    String password;
    String date;

    public User(String login, String password, String date) {
        this.login = login;
        this.password = password;
        this.date = date;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
