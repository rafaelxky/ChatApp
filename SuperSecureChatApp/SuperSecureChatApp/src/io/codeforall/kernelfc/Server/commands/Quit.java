package io.codeforall.kernelfc.Server.commands;

import io.codeforall.kernelfc.Server.ClientHandler;
import io.codeforall.kernelfc.Server.Server;

public class Quit extends Command{
    @Override
    public void execute(ClientHandler clientHandler, Server server, String[] in) {
        clientHandler.endConnection();
    }
}
