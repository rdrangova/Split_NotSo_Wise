package com.splitnotsowise.command;

import com.splitnotsowise.exceptions.InvalidArgumentCountException;
import com.splitnotsowise.communication.Server;
import com.splitnotsowise.utilities.User;

import java.io.PrintWriter;
import java.util.Arrays;

public class ClaimReceivedPaymentFromGroupCommand implements Command {
    private static final int INDEX_OF_PAYED_AMOUNT = 1;
    private static final int INDEX_OF_GROUP_NAME = 2;
    private static final int INDEX_OF_GROUP_MEMBER_USERNAME = 3;


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
            double amount = Double.parseDouble(content[INDEX_OF_PAYED_AMOUNT]);
            String groupName = content[INDEX_OF_GROUP_NAME];
            String groupMemberUsername = content[INDEX_OF_GROUP_MEMBER_USERNAME];

            User receiver = server.getRegisteredUser(clientUsername);
            receiver.receivePaymentFromGroupMember(groupMemberUsername, groupName, amount);

            User debtor = server.getRegisteredUser(groupMemberUsername);
            debtor.payGroup(groupMemberUsername, groupName, amount);

            writer.println("You get paid from " + groupMemberUsername + " from " + groupName + " successfully!");
        }
    }
}
