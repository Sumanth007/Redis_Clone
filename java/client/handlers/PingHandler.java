package client.handlers;
import client.Encoder;
import client.ResponseConstants;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
public class PingHandler extends BaseHandler implements IHandler {
  public PingHandler(Socket socket, BufferedReader in, BufferedWriter out) {
    super(socket, in, out);
  }
  @Override
  public void handle() throws IOException {
    sendResponse(Encoder.encodeSimpleResp(ResponseConstants.PONG));
  }
}