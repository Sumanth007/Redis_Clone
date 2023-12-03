import client.ClientHandler;
import config.ServerConfig;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        // You can use print statements as follows for debugging, they'll be visible
        // when running tests.
        ServerConfig.getInstance().loadConfigValues(args);
        int port = 6379;
        System.out.println("listening on tcp://localhost:" + port + " ....");
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setReuseAddress(true);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                Thread t = new Thread(new ClientHandler(clientSocket));
                t.start();
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
