package com.splitnotsowise.exceptions;

import java.io.PrintWriter;

public class InvalidArgumentCountException extends Exception {
    public InvalidArgumentCountException(String clientUsername) {
        System.err.println("Invalid input argument count from " + clientUsername);
    }
}
