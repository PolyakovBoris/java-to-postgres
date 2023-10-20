package org.example;
import java.sql.*;

public class QueryPostgres {

    // выборка данных SELECT принимает STRING login возвращает объект user
    public User getSelect(String login) {
        JDBCConnection newConnection = new JDBCConnection();
        ResultSet result1 = null;
        Statement statement = null;
        User user = null;
        try {

            statement = newConnection.getConnection().createStatement();
            String query = String.format("SELECT user_login.login, password, date, email " +
                    "FROM user_login " +
                    "INNER JOIN email " +
                    "ON user_login.login = email.login " +
                    "WHERE user_login.login = '%s'", login);
            result1 = statement.executeQuery(query);
            System.out.println("Выводим statement");
            while (result1.next()) {
                System.out.println("Номер в выборке #" + result1.getRow()
                        + "\t Логин в базе #" + result1.getString("login")
                        + "\t пароль в базе #" + result1.getString("password"));
                user = new User(result1.getString("login"), result1.getString("password"),
                        result1.getString("date"), result1.getString("email"));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return user;
    }

    // добавление данных INSERT принимает объект user
    public String makeInsert(User user) throws SQLException {
        JDBCConnection newConnection = new JDBCConnection();
        int counterInsert = 0;

        try (PreparedStatement preparedStatement = newConnection.getConnection().prepareStatement(
                "INSERT INTO user_login values(?, ?, ?); INSERT INTO email values(?, ?)")){
            preparedStatement.setString(1, user.login);
            preparedStatement.setString(2, user.password);
            preparedStatement.setString(3, user.date);
            preparedStatement.setString(4, user.login);
            preparedStatement.setString(5, user.email);
            preparedStatement.executeUpdate();
            counterInsert++;
        } catch (SQLException e) {
            throw new RuntimeException(e);
            }
        return String.format("%s", counterInsert);
    }
}
