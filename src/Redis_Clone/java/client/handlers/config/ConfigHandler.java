package client.handlers.config;
import client.Encoder;
import client.handlers.BaseHandler;
import client.handlers.IHandler;
import config.ServerConfig;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
public class ConfigHandler extends BaseHandler implements IHandler {
  public ConfigHandler(Socket socket, BufferedReader in, BufferedWriter out) {
    super(socket, in, out);
  }
  @Override
  public void handle() throws IOException {
    final ServerConfig config = ServerConfig.getInstance();
    final String cmd = readLine(1);
    if (cmd.equalsIgnoreCase("get")) {
      final String key = readLine(1);
      final String configValue = config.getConfigValue(key);
      // TODO: remove after debugging
      System.out.println("Found config value: " + configValue +
                         " for key: " + key);
      sendResponse(Encoder.encodeArray(Arrays.asList(key, configValue)));
    }
  }
}