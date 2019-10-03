package com.splitnotsowise.command;

import com.splitnotsowise.exceptions.InvalidArgumentCountException;
import com.splitnotsowise.utilities.Obligation;
import com.splitnotsowise.utilities.User;
import com.splitnotsowise.communication.Server;

import java.io.PrintWriter;
import java.util.Arrays;

public class SplitCommand implements Command {

    private static final int INDEX_OF_SPLIT_AMOUNT = 1;
    private static final int INDEX_OF_FRIEND_USERNAME = 2;
    private static final int START_INDEX_OF_REASON = 3;

    @Override
    public void execute(String clientUsername, String[] content, Server server, PrintWriter writer)
            throws InvalidArgumentCountException {

        if (!server.containsUser(clientUsername)){
            writer.println("You should register first!");
            return;
        }

        if (content.length < 4) {
            throw new InvalidArgumentCountException(clientUsername);
        } else {
            double amount = Double.parseDouble(content[INDEX_OF_SPLIT_AMOUNT]);
            String friendUsername = content[INDEX_OF_FRIEND_USERNAME];
            String reason = String.join(" ", Arrays.copyOfRange(content, START_INDEX_OF_REASON, content.length));

            if (!server.areFriends(clientUsername, friendUsername)) {
                writer.println("You can split sums only with friends");

            } else {
                double halfAmount = amount / 2;
                Obligation newObligation = new Obligation(halfAmount, reason);

                User creditor = server.getRegisteredUser(clientUsername);
                creditor.addObligationToCurrentUser(friendUsername, newObligation);

                User debtor = server.getRegisteredUser(friendUsername);
                debtor.addObligationOfCurrentUser(clientUsername, newObligation);

                //TODO notify debtor that he owes new money
                writer.println(amount + " split successfully between you and " + friendUsername);

            }
        }

    }

}
