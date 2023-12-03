package client;

import java.util.List;

public class Encoder {
    public static String encodeSimpleResp(String input) {
        if (input == null)
            return ResponseConstants.ERR_NULL;
        return "+" + input + "\r\n";
    }

    public static String encodeBulkString(String input) {
        if (input == null)
            return ResponseConstants.NIL;
        return "$" + input.getBytes().length + "\r\n" + input + "\r\n";
    }

    public static String encodeArray(List<String> elements) {
        if (elements.isEmpty()) {
            return "*0\r\n";
        }
        final StringBuilder resp = new StringBuilder();
        resp.append("*").append(elements.size()).append("\r\n");
        for (String element : elements) {
            resp.append("$").append(element.length()).append("\r\n");
            resp.append(element).append("\r\n");
        }
        final String respString = resp.toString();
        System.out.println("Returning resp array string: " +
                respString); // TODO: remove after debugging.
        return respString;
    }
}
