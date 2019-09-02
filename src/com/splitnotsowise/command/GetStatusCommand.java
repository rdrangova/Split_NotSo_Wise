package com.splitnotsowise.command;

import com.splitnotsowise.User;
import com.splitnotsowise.communication.Server;

import java.io.PrintWriter;
import java.util.HashSet;

public class GetStatusCommand implements Command {

    @Override
    public void execute(String clientUsername, String[] content, Server server, PrintWriter writer) {

        writer.println("Friends:");

        User current = server.getRegisteredUser(clientUsername);
        HashSet<String> friends = server.getFriendList(clientUsername);
        for (String friend : friends) {
            double obligation = current.getMyFriendObligationTo(friend);

            if (obligation > 0) {
                writer.println("You owe " + friend + " " + obligation);
            } else if (obligation < 0) {
                writer.println(friend + " owes you " + (-obligation));
            }
        }

        writer.println("Groups:"); //TODO this doesn't work
        for (String friend : friends) {
            double obligation = current.getMyGroupObligationTo(friend);

            if (obligation > 0) {
                writer.println("You owe " + friend + " " + obligation);
            } else if (obligation < 0) {
                writer.println(friend + " owes you " + (-obligation));
            }
        }
    }

}
