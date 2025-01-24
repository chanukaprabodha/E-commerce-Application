package lk.ijse;

import java.util.UUID;


public class IdGenerator {
    public static String generateUserId(String prefix) {
        String uniquePart = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
        return prefix + uniquePart;
    }
}
