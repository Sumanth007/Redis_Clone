import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

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
        //  serverSocket.setReuseAddress(true);
        //  // Wait for connection from client.
        //  clientSocket = serverSocket.accept();
        // serverSocket.setSoTimeout(1000); 
        System.out.println("Heeeyyy");
      while(true) {
			System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "..."); 

			Socket server = serverSocket.accept();
			System.out.println("Just connected to " + server.getRemoteSocketAddress()); 

			PrintWriter toClient = 
				new PrintWriter(server.getOutputStream(),true);
			BufferedReader fromClient =
				new BufferedReader(
						new InputStreamReader(server.getInputStream()));
			String line = fromClient.readLine();
      System.out.println("PONG");
			// System.out.println("Server received: " + line); 
			// toClient.println("Thank you for connecting to " + server.getLocalSocketAddress() + "\nGoodbye!");         
      server.close();

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
