package com.splitnotsowise.command;

import com.splitnotsowise.exceptions.InvalidArgumentCountException;
import com.splitnotsowise.communication.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;

public class CreateGroupCommand implements Command {

    private static final int INDEX_OF_GROUP_NAME = 1;
    private static final int START_INDEX_OF_GROUP_MEMBERS = 2;

    @Override
    public void execute(String clientUsername, String[] content, Server server, PrintWriter writer) throws InvalidArgumentCountException {

        if (!server.containsUser(clientUsername)){
            writer.println("You should register first!");
            return;
        }

        if (content.length < 3) {
            throw new InvalidArgumentCountException(clientUsername);

        } else {
            String groupName = content[INDEX_OF_GROUP_NAME];

            if (server.containsGroup(groupName)) {
                writer.println("Group name already used");

            } else {
                HashSet<String> groupMembers = new HashSet<>();


                groupMembers.add(clientUsername);
                for (int i = START_INDEX_OF_GROUP_MEMBERS; i < content.length; i++) {
                    if (!server.containsUser(content[i])){
                        writer.println(content[i]+ " is not registered user. Group can't be created" );
                        return;
                    }
                    groupMembers.add(content[i]);
                }

                server.addGroup(groupName, groupMembers);

                try {
                    String path = "resources\\com.splitnotsowise.communication.Server\\Groups";
                    Files.createDirectories(Paths.get(path));
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("Could not create a Groups directory");
                }

                String filePath = "resources\\com.splitnotsowise.communication.Server\\Groups\\" + groupName + "Group.txt";
                for (String member : groupMembers) {
                    server.addUsernameToFile(filePath, member);
                }

                writer.println("Group " + groupName + " was created successfully");
            }

        }

    }

}
