package org.example;
import java.sql.*;

public class QueryPostgres {

    // выборка данных SELECT принимает STRING login возвращает объект user
    public User getSelect(String login) {
        JDBCtest newConnection = new JDBCtest();
        ResultSet result1 = null;
        Statement statement = null;
        try {
            statement = newConnection.getConnection().createStatement();
            String query = String.format("SELECT * FROM newtable1 WHERE login = '%s'", login);
            result1 = statement.executeQuery(query);

            //result это указатель на первую строку с выборки
            //чтобы вывести данные мы будем использовать
            //метод next() , с помощью которого переходим к следующему элементу
            System.out.println("Выводим statement");
            User user = null;
            while (result1.next()) {
                System.out.println("Номер в выборке #" + result1.getRow()
                        + "\t Логин в базе #" + result1.getString("login")
                        + "\t пароль в базе #" + result1.getString("password"));
                user = new User(result1.getString("login"),result1.getString("password"),
                        result1.getString("date"));
            }

            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // добавление данных INSERT принимает объект user
    public int makeInsert(User user) throws SQLException {
        JDBCtest newConnection = new JDBCtest();

        try (PreparedStatement preparedStatement = newConnection.getConnection().prepareStatement(
                "INSERT INTO newtable1 values(?, ?, ?)")){
            preparedStatement.setString(1, user.login);
            preparedStatement.setString(2, user.password);
            preparedStatement.setString(3, user.date);
            preparedStatement.executeUpdate();
        }
        return 1;
    }
}
