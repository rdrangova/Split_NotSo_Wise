package com.splitnotsowise.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class ClientRunnable implements Runnable {

    private Socket socket;

    ClientRunnable(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            while (!socket.isClosed()) {

                System.out.println(reader.readLine());
            }

            System.out.println("Client socket is closed, stop waiting for server messages");

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
