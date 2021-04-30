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
    private static ArrayList<String> users = new ArrayList<>();
    private static ArrayList<MessagingThread> clients= new ArrayList<>();

    public static void main(String[] args) throws IOException, SQLException {
        ServerSocket server = new ServerSocket(8080,10);
        System.out.println("Now Serer is running");
        DbOperations.createUsersTable("users");
        DbOperations.createChatTable("chat_backup");
        while (true){
            Socket client = server.accept();

        }
    }
    public static void sendToAll(String user , String message){

    }
    static class MessagingThread extends Thread{
        String user="";
        BufferedReader input;
        PrintWriter output;

        public MessagingThread(Socket client) throws IOException, SQLException {
            input= new BufferedReader(new InputStreamReader(client.getInputStream()));
            output=new PrintWriter(client.getOutputStream(),true);

            user=input.readLine();
            users.add(user);
            DbOperations.addUserInDB(user);
        }
    }
}

