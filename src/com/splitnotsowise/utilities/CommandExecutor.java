package com.splitnotsowise.utilities;

import com.splitnotsowise.exceptions.InvalidArgumentCountException;
import com.splitnotsowise.command.*;
import com.splitnotsowise.communication.Server;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;


public class CommandExecutor {
    private static HashMap<String, Command> commands;

    static {
        commands = new HashMap<>();

        commands.put("register", new RegisterCommand());
        commands.put("login", new LoginCommand());
        commands.put("add-friend", new AddFriendCommand());
        commands.put("create-group", new CreateGroupCommand());
        commands.put("show-history", new ShowHistoryCommand());
        commands.put("split-group", new SplitWithGroupCommand());
        commands.put("received-payment", new ClaimReceivedPaymentCommand());
        commands.put("received-payment-group", new ClaimReceivedPaymentFromGroupCommand());
        commands.put("get-status", new GetStatusCommand());
        commands.put("split", new SplitCommand());
        commands.put("help", new HelpCommand());

    }

    public void executeCommand(String username, String commandLine, Server server, PrintWriter writer) {

        String[] commandLineContents = getCommandLineContents(commandLine);


        if (!commands.containsKey(commandLineContents[0])) {
            writer.println("Invalid command, use 'help' for further information");
            return;
        }

        try {
            commands.get(commandLineContents[0]).execute(username, commandLineContents, server, writer);
        } catch (InvalidArgumentCountException e) {
            writer.println("Invalid input, use 'help' for further information");
        }

    }

    private String[] getCommandLineContents(String commandLine) {
        commandLine = commandLine.trim();

        String[] commandLineContents = commandLine.split(" ");

        return Arrays.copyOfRange(commandLineContents, 0, commandLineContents.length);
    }
}
