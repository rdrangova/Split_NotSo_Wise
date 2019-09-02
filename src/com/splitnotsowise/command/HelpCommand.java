package com.splitnotsowise.command;

import com.splitnotsowise.communication.Server;

import java.io.PrintWriter;

public class HelpCommand implements Command {
    @Override
    public void execute(String clientUsername, String[] content, Server server, PrintWriter writer) {
        writer.println("You can use following commands:\n" +
                "register <username> <password>\n" +
                "login <username> <password>\n" +
                "add-friend <username>\n" +
                "create-group <group_name>  <username> -> minimum 2\n" +
                "split <amount> <friend_username> <reason_for_payment>\n" +
                "split-group <amount> <group_name> <reason_for_payment>\n" +
                "payed <amount> <username>\n" +
                "payed-group <amount> <groupName> <username>\n" +
                "get-status\n" +
                "show-history\n"
        );
    }
}
