package com.splitnotsowise.command;

import com.splitnotsowise.communication.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;

public class CreateGroupCommand implements Command {

    @Override
    public void execute(String clientUsername, String[] content, Server server, PrintWriter writer) {
        String[] tokens = content.split(" ");

        int index = 0;
        if (tokens.length >= 4) {
            String name = tokens[index++];
            HashSet<String> members = new HashSet<>();

            for (int i = index; i < tokens.length; i++) {
                members.add(tokens[i]);
            }

            if (server.containsGroup(name)) {
                writer.println("Group name already used");

            } else {
                server.addGroup(name, members);

                try {
                    String path = "resources\\com.splitnotsowise.communication.Server\\Groups";
                    Files.createDirectories(Paths.get(path));
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("could not create a Groups directory");
                }

                String path = "resources\\com.splitnotsowise.communication.Server\\Groups\\" + name + "Group.txt";
                for (String member : members) {
                    server.addUsernameToFile(path, member);
                }

                writer.println("Group " + name + " created successfully");
            }

        } else { //TODO this is not correct
            writer.println("Invalid input - groups must contain at least 3 people");
        }

    }

}
