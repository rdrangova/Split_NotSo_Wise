package com.splitnotsowise.communication;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private PrintWriter writer;

    public static void main(String[] args) throws IOException {
        new Client().run();
    }

    public void run() throws IOException {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String line = scanner.nextLine();

                int index = 0;

                String[] tokens = line.split(" ");
                String command = tokens[index++];

                if ("connect".equals(command)) {
                    if (tokens.length == 4) {
                        String host = tokens[index++];
                        int port = Integer.parseInt(tokens[index++]);
                        String username = tokens[index++];

                        connect(host, port, username);

                    } else {
                        System.out.println("Invalid input");
                    }

                } else if ("disconnect".equals(command)) {
                    writer.println("disconnect");
                    writer.close();
                    break;

                } else {
                    writer.println(line);
                    continue;

                }

            }
        }
    }

    private void connect(String host, int port, String username) {
        try {
            Socket socket = new Socket(host, port);
            System.out.println("Connected successfully");

            writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("connect " + username);

            ServerConnectionHandler runnable = new ServerConnectionHandler(socket);
            new Thread(runnable).start();

        } catch (IOException e) {
            System.err.println("Cannot connect to server on localhost:8080, make sure that the server is started");
            System.out.println();
        }

    }
}
