package clases;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBSConnector {
    Connection connection;
    Statement statement;

    public void createConnect() {
        {
            try {

                connection = DriverManager.getConnection("jdbc:mysql://localhost/labjava?serverTimezone=Europe/Moscow&useSSL=false&allowPublicKeyRetrieval=true&useSSL=false", "vladuser", "3208455258Ss");
                statement = connection.createStatement();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public JDBSConnector() throws SQLException {
    }

    public void addUser(User user) {
        try {
            String name = user.getName();
            String pass = user.getPass();
            String role = user.getRole();
            statement.executeUpdate("INSERT INTO `users` (`id`, `name`, `pass`, `role`) VALUES (NULL, '" + name + "','" + pass + "','" + role + "');");
            System.out.println("Пользователь " + name + " добавлен");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteByID(int id) {
        try {

            statement.executeUpdate("DELETE FROM `users` WHERE `users`.`id` = " + id + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getByID(int id) {
        try {
            PreparedStatement preparedStatement = null;
            preparedStatement = connection.prepareStatement("SELECT * FROM `users` WHERE `users`.`id` = ? ");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                User user = (new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                return user;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void changeRoleById(int id, String role) {
        try {
            statement.executeUpdate(" UPDATE `users` SET `role` = '" + role + "' WHERE `users`.`id` = " + id + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> show() {
        ArrayList<User> list = new ArrayList<User>();
        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM `users`;");
            while (rs.next()) {
                list.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }



    public int isExist(String password, String name) {
        try {
            PreparedStatement preparedStatement = null;
            preparedStatement = connection.prepareStatement("SELECT * FROM `users` WHERE `users`.`name` = ? AND `users`.`pass` = ?");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                if (password.equals(rs.getString(3)) & name.equals(rs.getString(2))) {
                    return rs.getInt(1);
                }
                return -1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("null");
            return -1;
        }
        return -1;
    }

}
