package client;
import client.handlers.EchoHandler;
import client.handlers.GetHandler;
import client.handlers.PingHandler;
import client.handlers.SetHandler;
import client.handlers.config.ConfigHandler;
import config.ServerConfig;
import storage.Storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
  private final Socket clientSocket;
  private final Storage storage;
  private final ServerConfig serverConfig;
  private BufferedWriter out;
  private BufferedReader in;
  public ClientHandler(Socket socket) {
    this.clientSocket = socket;
    this.storage = Storage.getInstance();
    this.serverConfig = ServerConfig.getInstance();
  }

  @Override
  public void run() {
    try {
      this.clientSocket.setSoTimeout(10000);
      this.in = new BufferedReader(
          new InputStreamReader(clientSocket.getInputStream()));
      this.out = new BufferedWriter(
          new OutputStreamWriter(clientSocket.getOutputStream()));
      String input;
      while (clientSocket.isConnected() && (input = in.readLine()) != null) {
        System.out.println("RECEIVED: " + input);
        final String lowerInput = input.toLowerCase();
        if (lowerInput.contains("ping")) {
          new PingHandler(clientSocket, in, out).handle();
        } else if (lowerInput.contains("echo")) {
          new EchoHandler(clientSocket, in, out).handle();
        } else if (lowerInput.contains("set")) {
          new SetHandler(clientSocket, in, out).handle();
        } else if (lowerInput.contains("get")) {
          new GetHandler(clientSocket, in, out).handle();
        } else if (lowerInput.contains("config")) {
          new ConfigHandler(clientSocket, in, out).handle();
        }
      }
    } catch (IOException e) {
      System.out.println("Give The Command");
      e.printStackTrace();
      closeSocket();
    }
  }

  private void closeSocket() {
    try {
      clientSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}