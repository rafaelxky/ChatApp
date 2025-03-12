package io.codeforall.kernelfc.Server.commands;

import io.codeforall.kernelfc.Server.ClientHandler;
import io.codeforall.kernelfc.Server.Server;

public abstract class Command {
    public abstract void execute(ClientHandler clientHandler, Server server, String[] in);
}
