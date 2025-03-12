package io.codeforall.kernelfc.Server.commands;

import io.codeforall.kernelfc.Server.ClientHandler;
import io.codeforall.kernelfc.Server.Server;

import java.io.IOException;
import java.util.Arrays;

public class SP extends Command{
    @Override
    public void execute(ClientHandler clientHandler, Server server, String[] in) {
        try {
            clientHandler.serverWrite("Server: " + String.join(" ",Arrays.copyOfRange(in, 1, in.length)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
