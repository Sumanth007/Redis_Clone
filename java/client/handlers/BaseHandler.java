package client.handlers;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
public class BaseHandler {
  final Socket socket;
  final BufferedReader in;
  final BufferedWriter out;
  public BaseHandler(Socket socket, BufferedReader in, BufferedWriter out) {
    this.socket = socket;
    this.in = in;
    this.out = out;
  }
  protected String readLine(int skipLineCount) throws IOException {
    for (int i = 0; i < skipLineCount; i++) {
      skipLine();
    }
    return this.in.readLine();
  }
  protected void skipLine() throws IOException { this.in.readLine(); }
  protected boolean hasNext() throws IOException { return in.ready(); }
  protected void sendResponse(String responseString) throws IOException {
    this.out.write(responseString);
    this.out.flush();
  }
}