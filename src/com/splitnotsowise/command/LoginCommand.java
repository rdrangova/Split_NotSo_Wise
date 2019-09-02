package com.splitnotsowise.command;

import com.splitnotsowise.communication.Server;

import java.io.PrintWriter;

public class LoginCommand implements Command {

    @Override
    public void execute(String clientUsername, String[] content, Server server, PrintWriter writer) {
        String[] tokens = content.split(" ");

        int index = 0;
        if (tokens.length == 2) {
            String username = tokens[index++];
            String password = tokens[index];

            if (server.containsUser(username) && server.containsAccount(username, password)) { //TODO something is wrong here
                System.out.println(username + " logged in");
                writer.println("Successfully logged in");

                // TODO notifications
            } else {
                writer.println("=> wrong username or password (or you are not registered)");

            }

        } else {
            writer.println("=> invalid input");
        }

    }

}
