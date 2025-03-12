package io.codeforall.kernelfc.Client;

import java.io.*;
import java.net.Socket;

public class Client {
    // todo: finish

    public static final String SERVERIP = "127.0.0.1";
    public static final int SERVERPORT = 8005;

    Socket clientSocket;

    public Client() {
        start();
    }

    private void start() {
        try {
            clientSocket = new Socket(SERVERIP, SERVERPORT);
            startListeningThread(clientSocket);

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (!clientSocket.isClosed()) {
                writeThread(clientSocket, reader.readLine());
            }
        } catch (IOException e) {
            System.out.println(" disconected from server ");
        }
    }

    public void writeThread(Socket clientSocket, String out) {
        new Thread(new Write(clientSocket, out)).start();
    }

    public void startListeningThread(Socket clientSocket) {
        new Thread(new Listen(clientSocket)).start();
    }
}

