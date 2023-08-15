import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
  public static void main(String[] args){
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.out.println("Logs from your program will appear here!");

    //  Uncomment this block to pass the first stage
       ServerSocket serverSocket = null;
       Socket clientSocket = null;
       int port = 6379;
       try {
         serverSocket = new ServerSocket(port);
         serverSocket.setReuseAddress(true);
        //  // Wait for connection from client.
        //  clientSocket = serverSocket.accept();
        // serverSocket.setSoTimeout(10000); 
        System.out.println("Heeeyyy");
      while(true) {
			System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "..."); 

			clientSocket = serverSocket.accept();
			System.out.println("Just connected to " + clientSocket.getRemoteSocketAddress()); 

			BufferedWriter toClient = 
				new BufferedWriter(
          new OutputStreamWriter(clientSocket.getOutputStream()));

			BufferedReader fromClient =
				new BufferedReader(
						new InputStreamReader(clientSocket.getInputStream()));

      while(true){
      String line = fromClient.readLine();
			System.out.println("Server received: " + line); 
      toClient.write("+PONG\r\n");
      toClient.flush();
      toClient.write("Press \" close \" to close the connection");
      toClient.flush();
      String check= fromClient.readLine();
      if(check.equals("close"))break;
    }
      
			// toClient.println("Thank you for connecting to " + server.getLocalSocketAddress() + "\nGoodbye!");         
      toClient.close();

       } 
      }
       catch (IOException e) {
         System.out.println("IOException: " + e.getMessage());
       } finally {
         try {
           if (clientSocket != null) {
             clientSocket.close();
           }
         } catch (IOException e) {
           System.out.println("IOException: " + e.getMessage());
         }
       }


  }
}
