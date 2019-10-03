package com.splitnotsowise.command;

import com.splitnotsowise.utilities.Obligation;
import com.splitnotsowise.utilities.User;
import com.splitnotsowise.communication.Server;

import java.io.PrintWriter;
import java.util.Map;


public class ShowHistoryCommand implements Command {

    @Override
    public void execute(String clientUsername, String[] content, Server server, PrintWriter writer) {

        if (!server.containsUser(clientUsername)) {
            writer.println("You should register first!");
            return;
        }

        User currentUser = server.getRegisteredUser(clientUsername);
        if (currentUser.getPaymentHistory().isEmpty()) {
            writer.println("You haven't paid anything yet");
        } else {
            Map<String, Obligation> history = currentUser.getPaymentHistory();

            for (String user : history.keySet()) {
                writer.println("You payed " + history.get(user).getAmount() + " because of "
                        + history.get(user).getReason() + " to " + user + " [" + history.get(user).getGroupName() + "] ");
            }
        }

    }

}
