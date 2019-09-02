package com.splitnotsowise.command;

import com.splitnotsowise.Payment;
import com.splitnotsowise.User;
import com.splitnotsowise.communication.Server;

import java.io.PrintWriter;

public class Split implements Command {

    @Override
    public void execute(String clientUsername, String[] content, Server server, PrintWriter writer) {
        String[] tokens = content.split(" ");

        int index = 0;
        if (tokens.length >= 3) {
            double amount = Double.parseDouble(tokens[index++]);
            String username = tokens[index];
            index = tokens[0].length() + username.length() + 2;
            String reason = content.substring(index);

            amount = amount / 2;
            if (!server.areFriends(clientUsername, username)) {
                writer.println("You can split sums only with friends");

            } else {

                Payment newPayment = new Payment(amount, reason, "friend");

                User creditor = server.getRegisteredUser(clientUsername);
                creditor.addPaymentOwedToMe(username, newPayment);
                server.addRegisteredUser(creditor);

                User debtor = server.getRegisteredUser(username);
                debtor.addPaymentOwedByMe(clientUsername, newPayment);
                server.addRegisteredUser(debtor);

                writer.println(amount + " split successfully between you and " + username);

            }
        } else {
            writer.println("Invalid input");
        }

    }

}
