package client.handlers;
import client.Encoder;
import client.ResponseConstants;
import storage.Storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
/**
 * Handle simple format of `set key value px milliseconds`
 */
public class SetHandler extends BaseHandler implements IHandler {
  public SetHandler(Socket socket, BufferedReader in, BufferedWriter out) {
    super(socket, in, out);
  }
  @Override
  public void handle() throws IOException {
    final Storage storage = Storage.getInstance();
    final String key = readLine(1);
    final String value = readLine(1);
    if (hasNext()) {
      final String argument = readLine(1).toLowerCase();
      if (argument.equalsIgnoreCase("px")) {
        final long timeoutMs = Long.parseLong(readLine(1));
        storage.putWithTimeout(key, value, timeoutMs);
      }
    } else {
      storage.put(key, value);
    }
    sendResponse(Encoder.encodeSimpleResp(ResponseConstants.OK));
  }
}