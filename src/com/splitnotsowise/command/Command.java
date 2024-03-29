package com.splitnotsowise.command;

import com.splitnotsowise.exceptions.InvalidArgumentCountException;
import com.splitnotsowise.communication.Server;

import java.io.PrintWriter;


public interface Command {

    void execute(String clientUsername, String[] content, Server server, PrintWriter writer)
            throws InvalidArgumentCountException;

}
