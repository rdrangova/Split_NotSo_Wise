package com.splitnotsowise.command;

import com.splitnotsowise.exceptions.InvalidArgumentCountException;
import com.splitnotsowise.communication.Server;

import java.io.PrintWriter;

public class AddFriendCommand implements Command {

    private static final int INDEX_OF_FRIEND = 1;

    @Override
    public void execute(String clientUsername, String[] content, Server server, PrintWriter writer) throws InvalidArgumentCountException {


        if (content.length != 2) {
                throw new InvalidArgumentCountException(clientUsername);

        } else {
            if (!server.containsUser(clientUsername)){
                writer.println("You should register first!");
                return;
            }
            String friend = content[INDEX_OF_FRIEND];

            if (!server.containsUser(friend)) {
                writer.println(friend + " is not a registered user");

            } else if (server.areFriends(clientUsername, friend)) {
                writer.println(friend + " is already in your friend list");

            } else if (clientUsername.equals(friend)) {
                writer.println("You cannot add yourself as a friend");

            } else {

                server.addContact(clientUsername, friend);
                server.addContact(friend, clientUsername);

                String path = "resources\\com.splitnotsowise.communication.Server\\" + clientUsername + "\\contactList.txt";
                server.addUsernameToFile(path, friend);

                path = "resources\\com.splitnotsowise.communication.Server\\" + friend + "\\contactList.txt";
                server.addUsernameToFile(path, clientUsername);

                writer.println(friend + " added successfully to friend list");
            }

        }

    }

}

