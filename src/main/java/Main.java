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
         serverSocket.setReuseAddress(true);
        //  // Wait for connection from client.
        //  clientSocket = serverSocket.accept();
        // serverSocket.setSoTimeout(10000); 
        System.out.println("Heeeyyy");
      while(true) {
			System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "..."); 

			clientSocket = serverSocket.accept();
			System.out.println("Just connected to " + clientSocket.getRemoteSocketAddress()); 

			PrintWriter toClient = 
				new PrintWriter(clientSocket.getOutputStream(),true);
			BufferedReader fromClient =
				new BufferedReader(
						new InputStreamReader(clientSocket.getInputStream()));
			String line = fromClient.readLine();
			System.out.println("Server received: " + line); 
      toClient.println("+PONG\r");
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
