package client.handlers;
import client.Encoder;
import storage.Storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
public class GetHandler extends BaseHandler implements IHandler {
  public GetHandler(Socket socket, BufferedReader in, BufferedWriter out) {
    super(socket, in, out);
  }
  @Override
  public void handle() throws IOException {
    final Storage storage = Storage.getInstance();
    final String key = readLine(1);
    sendResponse(Encoder.encodeBulkString(storage.get(key)));
  }
}