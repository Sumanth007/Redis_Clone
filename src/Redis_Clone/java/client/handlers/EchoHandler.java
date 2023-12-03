package client.handlers;
import client.Encoder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
public class EchoHandler extends BaseHandler implements IHandler {
  public EchoHandler(Socket socket, BufferedReader in, BufferedWriter out) {
    super(socket, in, out);
  }
  @Override
  public void handle() throws IOException {
    sendResponse(Encoder.encodeSimpleResp(readLine(1)));
  }
}