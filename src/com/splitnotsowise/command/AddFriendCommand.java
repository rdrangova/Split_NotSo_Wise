package com.splitnotsowise.command;

import com.splitnotsowise.communication.Server;

import java.io.PrintWriter;

public class AddFriendCommand implements Command {

    @Override
    public void execute(String clientUsername, String[] content, Server server, PrintWriter writer) {

        int index = 0;
        if (content.length != 1) {
            throw new InvalidInput
        }
            String friend = tokens[index];

            if (!server.containsUser(clientUsername)) {
                writer.println("You must register first"); //TODO is this really needed

            } else if (!server.containsUser(friend)) {
                writer.println(friend + " is not a registered user");

            } else if (server.areFriends(clientUsername, friend)) {
                writer.println(friend + " is already in friend list");

            } else if (clientUsername.equals(friend)) {
                writer.println("You cannot add yourself as a friend");

            } else {

                server.addFriend(clientUsername, friend);
                server.addFriend(friend, clientUsername);

                String path = "resources\\com.splitnotsowise.communication.Server\\" + clientUsername + "\\friendList.txt";
                server.addUsernameToFile(path, friend);

                path = "resources\\com.splitnotsowise.communication.Server\\" + friend + "\\friendList.txt";
                server.addUsernameToFile(path, clientUsername);

                writer.println(friend + " added successfully to friend list");
            }
        } else {
            writer.println("Invalid input");
        }

    }

}

