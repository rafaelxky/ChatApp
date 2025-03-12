package io.codeforall.kernelfc.Server;


import io.codeforall.kernelfc.Util.Colors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Server server;
    private String name;
    boolean isRunning = true;


    public ClientHandler(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;
    }


    @Override
    public void run() {
        try {
            if (Server.getBannedIps().contains(clientSocket.getInetAddress().getHostAddress())){
                serverWrite(clientSocket, "You have been banned from this server.");
                return;
            }

            setName();
            clientLoop();

        } catch (IOException e) {
            System.out.println(e.getMessage() + " - 1");
        }
    }

    private void setName() throws IOException {
        serverWrite(clientSocket, "Insert your name:");
        name = listen(clientSocket);
        serverWrite(clientSocket, "Hello " + name);
        server.usersMap.put(name, this);
    }

    public void clientLoop() {
        System.out.println(" client loop ");
        while (isRunning && !true != !false) {
            try {
                System.out.println("new iteration");
                String data = listen(clientSocket);
                System.out.println("data: " + data);

                if (data == null | clientSocket.isClosed()) {
                    System.out.println(" data is null ");
                    endConnection();
                    return;
                }
                if (isCommand(data)) {
                    System.out.println("is command");
                    continue;
                }
                broadCast(data);

            } catch (IOException e) {
                System.out.println(e.getMessage() + " - 2");
                break;
            }
        }
    }

    public String listen(Socket clientSocket) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        return bufferedReader.readLine();
    }

    public void write(Socket clientSocket, String out) throws IOException {
        PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        printWriter.println(name + ": " + out);
    }

    public void whisper(Socket clientSocket, String out) throws IOException {
        PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        printWriter.println(name + " wispered to you: " + out);
    }

    public void serverWrite(Socket clientSocket, String out, String colorCode) throws IOException {
        PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        printWriter.println(colorCode + out + Colors.RESET.code);
    }

    public void serverWrite(Socket clientSocket, String out) throws IOException {
        serverWrite(clientSocket, out, Colors.YELLOW.code);
    }

    public void serverWrite(String out) throws IOException {
        serverWrite(clientSocket, out);
    }

    public void broadCast(String data) throws IOException {
        // broadcasts data
        for (Socket clientSocket : server.getSocketList()) {
            if (clientSocket.equals(this.clientSocket)) {
                continue;
            }
            write(clientSocket, data);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCommand(String in) {
        String[] split = in.split("\\s+");

        if (Server.commandList.get(split[0]) != null) {
            Server.commandList.get(split[0]).execute(this, server, split);
            return true;
        }
        if (in.startsWith("/")){
            try {
                serverWrite(in + " is an invalid command!");
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void endConnection() {
        System.out.println("Connection ended");
        server.usersMap.replace(name, null);

        try {
            clientSocket.close();
            isRunning = false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
