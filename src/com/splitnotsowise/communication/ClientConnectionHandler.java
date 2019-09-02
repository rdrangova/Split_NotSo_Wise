package com.splitnotsowise.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.splitnotsowise.CommandExecutor;
import com.splitnotsowise.command.*;
public class ClientConnectionHandler implements Runnable {

    private CommandExecutor commandExecutor;
    private Server server;
    private PrintWriter writer;
    private BufferedReader reader;
    private Socket socket;
    private String username;

    public ClientConnectionHandler(Socket socket, String username, Server server) {
        try {
            this.socket = socket;
            this.username = username;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new PrintWriter(socket.getOutputStream(), true);
            this.commandExecutor = new CommandExecutor();
            this.server = server;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String commandLine = reader.readLine();
                String[] splitLine = commandLine.split(" ");

                if ("disconnect".equals(splitLine[0])) {
                    System.out.println("Client " + username + " is disconnecting...");
                    System.out.println("Closing this connection.");

                    socket.close();
                    writer.close();
                    reader.close();

                    System.out.println("Connection closed");
                    break;

                }

                //commandExecutor.setCommandLine(line);
                commandExecutor.executeCommand(username,commandLine,server,writer);
                //Command command = commandExecutor.getCommand(line);
                //String content = commandExecutor.getContent(line);

                //command.execute(username, content, server, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
