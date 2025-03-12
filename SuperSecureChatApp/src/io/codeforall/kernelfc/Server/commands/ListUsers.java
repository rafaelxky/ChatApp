package io.codeforall.kernelfc.Server.commands;

import io.codeforall.kernelfc.Server.ClientHandler;
import io.codeforall.kernelfc.Server.Server;
import io.codeforall.kernelfc.Util.Colors;

import java.io.IOException;

public class ListUsers extends Command {
    @Override
    public void execute(ClientHandler clientHandler, Server server, String[] in) {
        try {

            clientHandler.serverWrite(clientHandler.getClientSocket(), "");
            clientHandler.serverWrite(clientHandler.getClientSocket(),  "Online users: ", Colors.GREEN.code);
            for (String name : server.usersMap.keySet()) {
                if (server.usersMap.get(name) != null) {
                    clientHandler.serverWrite(clientHandler.getClientSocket(), name, Colors.GREEN.code);

                }
            }
            clientHandler.serverWrite(clientHandler.getClientSocket(), "Offline users: ", Colors.RED.code);
            for (String name : server.usersMap.keySet()) {
                if (server.usersMap.get(name) == null) {
                    clientHandler.serverWrite(clientHandler.getClientSocket(),name, Colors.RED.code);
                }
            }
            clientHandler.serverWrite(clientHandler.getClientSocket(), "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
