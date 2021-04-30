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

public class DbOperations {
    private static volatile Connection connection;

    public static Connection getConnection() throws SQLException {

        if (connection == null) {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat_application", "root", "chaman@123");
        }

        return connection;

    }
}

