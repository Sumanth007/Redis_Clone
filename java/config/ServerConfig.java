package config;
import java.util.concurrent.ConcurrentHashMap;
import storage.Storage;
public class ServerConfig {
  private static volatile ServerConfig instance = null;
  private final ConcurrentHashMap<String, String> map;
  private ServerConfig() { map = new ConcurrentHashMap<>(); }
  public static ServerConfig getInstance() {
    if (instance == null) {
      synchronized (Storage.class) {
        if (instance == null) {
          instance = new ServerConfig();
        }
      }
    }
    return instance;
  }
  public void loadConfigValues(String[] arguments) {
    if (arguments == null || arguments.length == 0)
      return;
    for (int i = 0; i < arguments.length; i++) {
      final String argumentKey = arguments[i];
      switch (argumentKey) {
      case "--dir":
        final String directoryName = arguments[++i];
        putConfigValue("dir", directoryName);
        break;
      case "--dbfilename":
        final String fileName = arguments[++i];
        putConfigValue("dbfilename", fileName);
        break;
      default:
        break;
      }
    }
  }

  public void putConfigValue(String key, String value) { map.put(key, value); }
  public String getConfigValue(String key) { return map.get(key); }
}