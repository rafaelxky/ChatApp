package io.codeforall.kernelfc.Server.commands;

import io.codeforall.kernelfc.Server.ClientHandler;
import io.codeforall.kernelfc.Server.Server;

import java.io.IOException;
import java.net.Socket;

public class Kick extends Command {
    @Override
    public void execute(ClientHandler clientHandler, Server server, String[] in) {
        try {

            if (server.usersMap.get(in[1]).getClientSocket() == null) {
                clientHandler.serverWrite(clientHandler.getClientSocket(), in[1] + " is not a valid user!");
                return;
            }
            Socket socket = server.usersMap.get(in[1]).getClientSocket();
            clientHandler.serverWrite(socket, "You have been kicked from the server");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        server.usersMap.get(in[1]).endConnection();
    }
}
