/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 30-Apr-21
 *   Time: 11:34 AM
 *   File: DbOperations.java
 */

package com.example.chat_app;

import java.sql.*;

public class DbOperations {
    private static volatile Connection connection;

    public static Connection getConnection() throws SQLException {

        if (connection == null) {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat_application", "root", "1122");
        }
        return connection;
    }

    public static void addUserInDB(String user) throws SQLException {
        getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users VALUES(null ,?,?)");
        preparedStatement.setString(1, user);
        preparedStatement.setDate(2, new Date(System.currentTimeMillis()));
        int rows = preparedStatement.executeUpdate();
        if (rows > 0) {
            System.out.println("Successfully inserted");
        } else {
            System.out.println("Unable to add user to DB");
        }
    }

    public static void createUsersTable(String users) throws SQLException {
        getConnection();
        Statement statement = connection.createStatement();
        String sql = "CREATE TABLE " + users + "(id int primary key auto_increment, name VARCHAR(30), joining_time date)";
        statement.execute(sql);
    }

    public static void createChatTable(String name) throws SQLException {
        getConnection();
        Statement statement = connection.createStatement();
        String sql = "CREATE TABLE " + name + "(msg_id VARCHAR(40) primary key, name VARCHAR(30), msg VARCHAR(200))";
        statement.execute(sql);
    }

    public static void chatBackUp(String user, String msg_id, String msg) throws SQLException {

        getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO chat_backup VALUES (?, ?, ?)");
        preparedStatement.setString(1, msg_id);
        preparedStatement.setString(2, user);
        preparedStatement.setString(3, msg);
        int rows_affected = preparedStatement.executeUpdate();

        if (rows_affected > 0) {
            System.out.println("succesfully inserted the msg in DB");
        } else {
            System.out.println("unable to insert the msg in DB");
        }

    }
}

