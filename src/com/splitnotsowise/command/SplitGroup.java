package com.splitnotsowise.command;

import com.splitnotsowise.Payment;
import com.splitnotsowise.User;
import com.splitnotsowise.communication.Server;

import java.io.PrintWriter;
import java.util.HashSet;

public class SplitGroup implements Command {

    @Override
    public void execute(String clientUsername, String[] content, Server server, PrintWriter writer) {
        String[] tokens = content.split(" ");

        int index = 0;
        if (tokens.length >= 3) {
            double amount = Double.parseDouble(tokens[index++]);
            String groupName = tokens[index];
            index = tokens[0].length() + groupName.length() + 2;
            String reason = content.substring(index);

            HashSet<String> group = server.getGroup(groupName);
            amount = amount / (double) group.size(); //TODO should we cast this if amount is double

            Payment newPayment = new Payment(amount, reason, groupName);
            User creditor = server.getRegisteredUser(clientUsername);

            for (String member : group) {
                User debtor = server.getRegisteredUser(member);
                debtor.addPaymentOwedByMe(clientUsername, newPayment);
                server.addRegisteredUser(debtor);

                creditor.addPaymentOwedToMe(member, newPayment);
            }

            server.addRegisteredUser(creditor);
            writer.println(amount + " split successfully between members of " + groupName);
        } else {
            writer.println("Invalid input"); //TODO invalid input exception
        }

    }

}
