/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 30-Apr-21
 *   Time: 11:34 AM
 *   File: Server.java
 */

package com.example.chat_app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class Server {
    private static final ArrayList<String> users = new ArrayList<>();
    private static final ArrayList<MessagingThread> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException, SQLException {
        ServerSocket server = new ServerSocket(8080, 10);
        System.out.println("Now Serer is running");
        DbOperations.createUsersTable("users");
        DbOperations.createChatTable("chat_backup");
        while (true) {
            Socket client = server.accept();
            MessagingThread thread = new MessagingThread(client);
            clients.add(thread);
            thread.start();
        }
    }

    public static void sendToAll(String user, String message) {
        for (MessagingThread c : clients) {
            if (!c.getUser().equals(user)) {
                c.sendMessage(user, message);
            } else {
                c.sendToMe(user, message);
            }
        }
    }

    static class MessagingThread extends Thread {
        String user = "";
        BufferedReader input;
        PrintWriter output;

        public MessagingThread(Socket client) throws IOException, SQLException {
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            output = new PrintWriter(client.getOutputStream(), true);

            user = input.readLine();
            users.add(user);
            DbOperations.addUserInDB(user);
        }

        public void sendMessage(String user, String message) {
            output.println(user + " : " + message);
        }

        public void sendToMe(String user, String message) {
            output.println("You: " + message);
        }

        public String getUser() {
            return user;
        }
        public void saveInDB(String chatUser, String msg) throws SQLException {
            String msg_id=chatUser+"_"+System.currentTimeMillis();
            DbOperations.chatBackUp(user,msg_id,msg);
        }

        @Override
        public void run() {
            String line;
            try {
                while (true) {
                    line = input.readLine();
                    if (line.equals("end")) {
                        clients.remove(this);
                        users.remove(user);
                        break;
                    }else {
                        sendToAll(user, line);
                        saveInDB(user, line);
                    }
                }
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}

