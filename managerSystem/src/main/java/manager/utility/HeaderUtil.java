package manager.utility;

import java.util.Base64;

public class HeaderUtil {

    public static boolean isAdmin(String header) {
        // Decoding logic for Base64 encoded header to check role
        return new String(Base64.getDecoder().decode(header)).contains("\"role\":\"admin\"");
    }

    public static int extractUserId(String header) {
        // Decoding logic for Base64 encoded header to extract user ID
        return Integer.parseInt(new String(Base64.getDecoder().decode(header)).split(",")[0].split(":")[1]);
    }
}