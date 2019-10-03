package com.splitnotsowise.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.splitnotsowise.exceptions.InvalidArgumentCountException;
import com.splitnotsowise.utilities.CommandExecutor;

public class ClientConnectionRunnable implements Runnable {

    private Socket socket;
    private String username;
    private Server server;
    private BufferedReader reader;
    private PrintWriter writer;
    private CommandExecutor commandExecutor;

    public ClientConnectionRunnable(Socket socket, String username, Server server) {
        try {
            this.socket = socket;
            this.username = username;
            this.server = server;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new PrintWriter(socket.getOutputStream(), true);
            this.commandExecutor = new CommandExecutor();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String commandInput = reader.readLine();
                String[] commandInputComponents = commandInput.split(" ");

                if ("disconnect".equals(commandInputComponents[0])) {
                    System.out.println("Client " + username + " is disconnecting...");
                    System.out.println("Closing this connection.");

                    socket.close();
                    writer.close();
                    reader.close();

                    System.out.println("Connection closed");
                    break;

                }
                commandExecutor.executeCommand(username,commandInput,server,writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
