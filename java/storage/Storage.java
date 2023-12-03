package storage;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
public class Storage {
  private static volatile Storage instance = null;
  private ConcurrentHashMap<String, String> map;
  private Storage() { map = new ConcurrentHashMap<>(); }
  public static Storage getInstance() {
    if (instance == null) {
      synchronized (Storage.class) {
        if (instance == null) {
          instance = new Storage();
        }
      }
    }
    return instance;
  }
  public void put(String key, String value) { map.put(key, value); }
  public void putWithTimeout(final String key, String value, long timeout) {
    map.put(key, value);
    new Timer().schedule(new TimerTask() {
      @Override
      public void run() {
        map.remove(key);
      }
    }, timeout);
  }
  public String get(String key) { return map.get(key); }
}