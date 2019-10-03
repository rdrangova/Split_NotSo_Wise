package com.splitnotsowise.communication;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private PrintWriter writer;
    private static final int INDEX_OF_COMMAND = 0;
    private static final int INDEX_OF_HOST = 1;
    private static final int INDEX_OF_PORT = 2;
    private static final int INDEX_OF_USERNAME = 3;

    public static void main(String[] args) throws IOException {
        new Client().run();
    }

    public void run() {
        System.out.println("To connect write this command: connect <host> <port> <username> ");
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String input = scanner.nextLine();

                String[] tokens = input.split(" ");
                String command = tokens[INDEX_OF_COMMAND];

                if ("connect".equals(command)) {
                    if (tokens.length == 4) {
                        String host = tokens[INDEX_OF_HOST];
                        int port = Integer.parseInt(tokens[INDEX_OF_PORT]);
                        String username = tokens[INDEX_OF_USERNAME];

                        connect(host, port, username);
                    } else {
                        System.out.println("Invalid input");
                    }

                } else if ("disconnect".equals(command)) {
                    writer.println("disconnect");
                    writer.close();
                    break;

                } else {
                    writer.println(input);
                }

            }
        }
    }

    private void connect(String host, int port, String username) {
        try {
            Socket socket = new Socket(host, port);

            writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("connect " + username);

            ClientRunnable clientRunnable = new ClientRunnable(socket);
            new Thread(clientRunnable).start();

        } catch (IOException e) {
            System.err.println("Cannot connect to server on localhost:8080, make sure that the server is started");
        }

    }
}
