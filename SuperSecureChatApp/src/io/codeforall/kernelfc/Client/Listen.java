package io.codeforall.kernelfc.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Listen implements Runnable {
    Socket clientSocket;
    // todo: finish
    // always listen
    // every received message, new thread to write to terminal
    // repeat

    public Listen(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        System.out.println(" running listen thread");
        while (true) {
            try {
                System.out.println(listen(clientSocket));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String listen(Socket clientSocket) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        return bufferedReader.readLine();
    }
}
