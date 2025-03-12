package io.codeforall.kernelfc.Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Write implements Runnable {
    Socket clientSocket;
    String out;
    // todo: finish

    public Write(Socket clientSocket, String out) {
        this.clientSocket = clientSocket;
        this.out = out;
    }

    @Override
    public void run() {
        try {
            write(clientSocket, out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(Socket clientSocket, String out) throws IOException {
        PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        printWriter.println(out);
    }
}
