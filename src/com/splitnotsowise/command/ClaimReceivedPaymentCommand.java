package com.splitnotsowise.command;

import com.splitnotsowise.exceptions.InvalidArgumentCountException;
import com.splitnotsowise.utilities.User;
import com.splitnotsowise.communication.Server;

import java.io.PrintWriter;
import java.util.Arrays;

public class ClaimReceivedPaymentCommand implements Command {

    private static final int INDEX_OF_PAYED_AMOUNT = 1;
    private static final int INDEX_OF_PAYER_USERNAME = 2;

    @Override
    public void execute(String clientUsername, String[] content, Server server, PrintWriter writer) throws InvalidArgumentCountException {

        if (!server.containsUser(clientUsername)){
            writer.println("You should register first!");
            return;
        }

        if (content.length < 3) {
            throw new InvalidArgumentCountException(clientUsername);

        } else {

            double amount = Double.parseDouble(content[INDEX_OF_PAYED_AMOUNT]);
            String payerUsername = content[INDEX_OF_PAYER_USERNAME];

            User receiver = server.getRegisteredUser(clientUsername);
            receiver.receivePaymentFromFriend(payerUsername, amount);

            User debtor = server.getRegisteredUser(payerUsername);
            debtor.payFriend(clientUsername, amount);

            writer.println("You claimed payment from " + payerUsername + " successfully");
            //TODO notifications
        }
    }

}
