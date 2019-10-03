package com.splitnotsowise.command;

import com.splitnotsowise.utilities.User;
import com.splitnotsowise.communication.Server;

import java.io.PrintWriter;
import java.util.HashSet;

public class GetStatusCommand implements Command {

    @Override
    public void execute(String clientUsername, String[] content, Server server, PrintWriter writer) {

        if (!server.containsUser(clientUsername)) {
            writer.println("You should register first!");
            return;
        }
        User currentUser = server.getRegisteredUser(clientUsername);
        HashSet<String> friends = server.getContactList(clientUsername);
        //writer.println("Friends:");
        if (friends.isEmpty()) {
            writer.println("You don't have any obligations");
            return;
        }

        for (String friend : friends) {
            double obligation = currentUser.getCurrentUserObligationsToFriend(friend);

            if (obligation > 0) {
                writer.println("You owe " + friend + " " + obligation);
            } else if (obligation < 0) {
                writer.println(friend + " owes you " + (-obligation));
            }
        }

        //writer.println("Groups:");
        for (String friend : friends) {
            double obligation = currentUser.getUsersObligationToGroupMember(friend);

            if (obligation > 0) {
                writer.println("You owe " + friend + " " + obligation + "for group stuff");
            } else if (obligation < 0) {
                writer.println(friend + " owes you " + (-obligation) + "for group stuff");
            }
        }
    }
}


