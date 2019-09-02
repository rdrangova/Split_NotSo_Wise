package com.splitnotsowise.command;

import com.splitnotsowise.User;
import com.splitnotsowise.communication.Server;

import java.io.PrintWriter;

public class GetPayedCommand implements Command {
//TODO rewrite the whole class, something is not okay
    @Override
    public void execute(String clientUsername, String[] content, Server server, PrintWriter writer) {

        String[] tokens = content.split(" ");

        int index = 0;
        if (tokens.length < 3) {
            writer.println("Invalid input");
            //TODO error handling not only here
        }
        else {

            double amount = Double.parseDouble(tokens[index++]);
            String username = tokens[index];

            index = tokens[0].length() + username.length() + 2; //TODO this doesn't seem okay
            String reason = content.substring(index);

            User creditor = server.getRegisteredUser(clientUsername);
            creditor.getFriendPaid(username, amount, reason);
            server.addRegisteredUser(creditor);

            User debtor = server.getRegisteredUser(username);
            debtor.payFriend(clientUsername, amount, reason);
            server.addRegisteredUser(debtor);

            writer.println("Friend payed you successfully");
        }
    }

}
