package com.splitnotsowise.command;

import com.splitnotsowise.exceptions.InvalidArgumentCountException;
import com.splitnotsowise.utilities.Obligation;
import com.splitnotsowise.utilities.User;
import com.splitnotsowise.communication.Server;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;

public class SplitWithGroupCommand implements Command {

    private static final int INDEX_OF_SPLIT_AMOUNT = 1;
    private static final int INDEX_OF_GROUP_NAME = 2;
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
        }
        double amount = Double.parseDouble(content[INDEX_OF_SPLIT_AMOUNT]);
        String groupName = content[INDEX_OF_GROUP_NAME];
        String reason = String.join(" ", Arrays.copyOfRange(content, START_INDEX_OF_REASON, content.length));

        if (server.getGroup(groupName) == null) {
            writer.println("Group with name " + groupName + "doesn't exist");
            throw new InvalidArgumentCountException(clientUsername);
        } else {
            HashSet<String> group = server.getGroup(groupName);
            double splitAmount = amount / (double) group.size();

            Obligation newObligation = new Obligation(splitAmount, groupName, reason);
            User creditor = server.getRegisteredUser(clientUsername);

            for (String member : group) {
                User debtor = server.getRegisteredUser(member);
                debtor.addObligationOfCurrentUser(clientUsername, newObligation);
                //TODO write notifications for debtors

                creditor.addObligationToCurrentUser(member, newObligation);
            }

            writer.println(amount + " split successfully between members of " + groupName);
        }

    }

}
