package com.splitnotsowise.command;

import com.splitnotsowise.Payment;
import com.splitnotsowise.User;
import com.splitnotsowise.communication.Server;

import java.io.PrintWriter;
import java.util.ArrayList;


public class ShowHistoryCommand implements Command {

    @Override
    public void execute(String clientUsername, String[] content, Server server, PrintWriter writer) {
        User currentUser = server.getRegisteredUser(clientUsername);
        ArrayList<Payment> history = currentUser.getPaymentHistory();

        for (Payment payment : history) {
            writer.println("You got payed "+payment.getAmount()+" because of "+payment.getReason());
        }

    }

}
