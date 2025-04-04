package io.codeforall.kernelfc.Server.commands;

import io.codeforall.kernelfc.Server.ClientHandler;
import io.codeforall.kernelfc.Server.Server;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class Whisper extends Command {
    @Override
    public void execute(ClientHandler clientHandler, Server server, String[] in) {

        try {

            if (server.usersMap.get(in[1]).getClientSocket() == null){
                clientHandler.serverWrite(clientHandler.getClientSocket(), in[1] + " is not a valid user!");
                return;
            }
            Socket socket = server.usersMap.get(in[1]).getClientSocket();
            clientHandler.whisper(socket, String.join(" ",Arrays.copyOfRange(in, 2, in.length)));
            clientHandler.serverWrite(clientHandler.getClientSocket(), "Sent: " + String.join(" ", Arrays.copyOfRange(in, 2, in.length)) + " - to - " + in[1]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
