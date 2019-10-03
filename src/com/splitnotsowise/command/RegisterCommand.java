package com.splitnotsowise.command;

import com.splitnotsowise.exceptions.InvalidArgumentCountException;
import com.splitnotsowise.utilities.User;
import com.splitnotsowise.communication.Server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class RegisterCommand implements Command {

    private static final int INDEX_OF_USERNAME = 1;
    private static final int INDEX_OF_PASSWORD = 2;

    @Override
    public void execute(String clientUsername, String[] content, Server server, PrintWriter writer) throws InvalidArgumentCountException {

        if (content.length != 3) {
            throw new InvalidArgumentCountException(clientUsername);
        } else {

            String username = content[INDEX_OF_USERNAME];
            String password = content[INDEX_OF_PASSWORD];

            if (server.containsUser(username)) {
                writer.println("User with this name already exists");
            } else {

                server.addRegisteredUser(new User(username, password));

                try {

                    String path = "resources\\com.splitnotsowise.communication.Server";
                    Files.createDirectories(Paths.get(path));

                    path = "resources\\com.splitnotsowise.communication.Server\\" + username;
                    Files.createDirectories(Paths.get(path));

                } catch (IOException e) {
                    writer.println("There were problems with creating your account");
                    e.printStackTrace();
                }

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

                server.createContactList(username);


                System.out.println(username + " registered");
                writer.println("You registered successfully, now you can login");

            }

        }

    }

}
