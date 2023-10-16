package org.example;

import java.sql.SQLException;
import java.util.Random;

public class App {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        QueryPostgres queryPostgres = new QueryPostgres();

        // запрос данных по пользователю с логином b
        User user = queryPostgres.getSelect("b");

        // новый пользователь
        user.login = user.login + new Random().nextInt(1000 - 1);

        // добавление пользователя
        System.out.println(queryPostgres.makeInsert(user));
    }
}
