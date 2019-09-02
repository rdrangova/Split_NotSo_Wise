package com.splitnotsowise.command;

import com.splitnotsowise.User;
import com.splitnotsowise.communication.Server;

import java.io.PrintWriter;

public class PayedGroup implements Command {
//TODO rewrite
    @Override
    public void execute(String clientUsername, String[] content, Server server, PrintWriter writer) {
        String[] tokens = content.split(" ");

        int index = 0;
        if (tokens.length >= 4) {
            double amount = Double.parseDouble(tokens[index++]);
            String username = tokens[index++];
            String groupName = tokens[index];

            index = tokens[0].length() + username.length() + groupName.length() + 3;
            String reason = content.substring(index);

            User creditor = server.getRegisteredUser(clientUsername);
            creditor.getGroupPaid(username, groupName, amount, reason);
            server.addRegisteredUser(creditor);

            User debtor = server.getRegisteredUser(username);
            debtor.payGroup(username, groupName, amount, reason);
            server.addRegisteredUser(debtor);

            writer.println("=> you payed group successfully");
        }
    }

}
