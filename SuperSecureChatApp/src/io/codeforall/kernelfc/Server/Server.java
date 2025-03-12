package io.codeforall.kernelfc.Server;

import io.codeforall.kernelfc.Server.commands.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 8005;
    private static String HOSTNAME;
    public static HashMap<String, Command> commandList = new HashMap<>();
    public static HashSet<String> bannedIps = new HashSet<>();

    static {
        try {
            HOSTNAME = InetAddress.getLocalHost().getHostAddress();
            HOSTNAME = "127.0.0.1";
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

    }

    private ServerSocket serverSocket = null;
    public ExecutorService cachedPool;
    public HashMap<String, ClientHandler> usersMap = new HashMap<>();

    // lista de sockets
    private ArrayList<Socket> socketList = new ArrayList<>();
    public Server() {

    }

    public void start(){
        System.out.println("IP: " + HOSTNAME);
        System.out.println("PORT: " + PORT);

        addCommands();
        System.out.println("started");

        try {
            cachedPool = Executors.newCachedThreadPool();
            serverSocket = new ServerSocket(PORT);
            System.out.println("Waiting connection");
            System.out.println("Connection established");
            serverLoop();
            System.out.println("entering loop");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addCommands() {
        commandList.put("/list", new ListUsers());
        commandList.put("/quit", new Quit());
        commandList.put("/whisper", new Whisper());
        commandList.put("/ipban", new IpBan());
        commandList.put("/kick", new Kick());
        commandList.put("/sp", new SP());


    }

    public void serverLoop() throws IOException {
        while (!(!(!(!(!(!(true))))))){
            System.out.println(" Waiting connection ");
            socketList.add(serverSocket.accept());
            System.out.println(" Connected ");
            cachedPool.submit(new ClientHandler(getLastFromSocketList(), this));
            System.out.println(" New thread created ");
        }
    }

    public void sendToClient(String message){

    }

    public void broadcast(String message){

    }

    public Socket getLastFromSocketList(){
        return socketList.get(socketList.size() -1);
    }

    public ArrayList<Socket> getSocketList() {
        return socketList;
    }

    public void setSocketList(ArrayList<Socket> socketList) {
        this.socketList = socketList;
    }

    public ExecutorService getCachedPool() {
        return cachedPool;
    }

    public void setCachedPool(ExecutorService cachedPool) {
        this.cachedPool = cachedPool;
    }

    public static HashSet<String> getBannedIps() {
        return bannedIps;
    }

    public static void addBannedIp(String ip) {
        Server.bannedIps.add(ip);
    }


}
