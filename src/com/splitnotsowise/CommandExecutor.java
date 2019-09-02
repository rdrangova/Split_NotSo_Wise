package com.splitnotsowise;

import com.splitnotsowise.command.*;
import com.splitnotsowise.communication.Server;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;


public class CommandExecutor {
    private static HashMap<String, Command> commands;

    static {
        commands = new HashMap<>();

        commands.put("register", new Registration());
        commands.put("login", new LoginCommand());
        commands.put("add-friend", new AddFriendCommand());
        commands.put("create-group", new CreateGroupCommand());
        commands.put("show-history", new ShowHistoryCommand());
        commands.put("split-group", new SplitGroup());
        commands.put("payed", new GetPayedCommand());
        commands.put("payed-group", new PayedGroup());
        commands.put("get-status", new GetStatusCommand());
        commands.put("split", new Split());
        commands.put("help", new HelpCommand());
    }

    public void executeCommand(String username, String commandLine, Server server, PrintWriter writer){

        String[] commandLineContents = getCommandLineContents(commandLine);

        commands.get(commandLineContents[0]).execute(username,commandLineContents,server,writer);
    }

    public String [] getCommandLineContents(String commandLine) {
        commandLine = commandLine.trim();
        String[] commandLineContents = commandLine.split(" ");

        if (commandLineContents[0].equals("get-status") || commandLineContents[0].equals("show-history")) {
            return null;
        }

        return Arrays.copyOfRange(commandLineContents,1,commandLineContents.length);

    }
}
