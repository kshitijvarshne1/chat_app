/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 30-Apr-21
 *   Time: 11:34 AM
 *   File: DbOperations.java
 */

package com.example.chat_app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbOperations {
    private static volatile Connection connection;

    public static Connection getConnection() throws SQLException {

        if (connection == null) {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat_application", "root", "");
        }
        return connection;
    }

    public static void createUsersTable(String users) throws SQLException {
        getConnection();
        Statement statement = connection.createStatement();
        String sql = "CREATE TABLE " + users + "(id int primary key auto_increment, name VARCHAR(30), joining_time date)";
        statement.execute(sql);
    }

    public static void createChatTable(String chat_backup) throws SQLException {
        getConnection();
        Statement statement =connection.createStatement();
        String sql= "CREATE TABLE "+chat_backup+" (msg_id VARCHAR(40) primary key, name VARCHAR(30), msg VARCHAR(200))";
    }
}

