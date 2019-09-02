package com.splitnotsowise.command;

import com.splitnotsowise.User;
import com.splitnotsowise.communication.Server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Registration implements Command {

    @Override
    public void execute(String clientUsername, String[] content, Server server, PrintWriter writer) {
        String[] tokens = content.split(" ");

        int index = 0;
        if (tokens.length == 2) {
            String username = tokens[index++];
            String password = tokens[index];

            if (!server.containsUser(username)) {
                server.addRegisteredUser(new User(username, password));

                try {
                    // create com.splitnotsowise.communication.Server directory
                    String path = new String("resources\\com.splitnotsowise.communication.Server");
                    Files.createDirectories(Paths.get(path));

                    // create user directory
                    path = "resources\\com.splitnotsowise.communication.Server\\" + username;
                    Files.createDirectories(Paths.get(path));

                } catch (IOException e) {
                    e.printStackTrace();
                }

                // add user to file
                File file = new File("resources\\com.splitnotsowise.communication.Server\\registeredUsers.txt");
                try (FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
                     BufferedWriter bufferedWriter = new BufferedWriter(fw)) {

                    if (!file.exists()) {
                        file.createNewFile();
                    }

                    bufferedWriter.write("username: " + username + " ");
                    bufferedWriter.write("password: " + password);
                    bufferedWriter.newLine();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                server.createFriendList(username);

                System.out.println(username + " registered");
                writer.println("=> registered successfully");

            } else {
                writer.println("=> username already in database");
            }

        } else {
            writer.println("=> invalid input");
        }

    }

}
