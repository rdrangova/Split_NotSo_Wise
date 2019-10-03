package com.splitnotsowise.command;

import com.splitnotsowise.exceptions.InvalidArgumentCountException;
import com.splitnotsowise.communication.Server;

import java.io.PrintWriter;

public class LoginCommand implements Command {

    private static final int INDEX_OF_USERNAME = 1;
    private static final int INDEX_OF_PASSWORD = 2;

    @Override
    public void execute(String clientUsername, String[] content, Server server, PrintWriter writer) throws InvalidArgumentCountException {

        if (content.length != 3) {

            throw new InvalidArgumentCountException(clientUsername);

        } else {

            String username = content[INDEX_OF_USERNAME];
            String password = content[INDEX_OF_PASSWORD];
            if (!server.containsUser(username)){
                writer.println("You are not registered or your username is not correct");
            } else if ( !server.isAccountValid(username, password)) {
                writer.println("Wrong password");
            }else {
                    System.out.println(username + " logged in");
                    writer.println("Successfully logged in");
                    server.getRegisteredUser(username).printNotifications(writer);

                    // TODO notifications
            }

        }

    }

}
