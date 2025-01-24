package lk.ijse.Util;

import java.util.UUID;

/**
 * Author: Chanuka Prabodha
 * Date: 2025-01-16
 * Time: 03:21 PM
 * Description: Generate a random UUID and take the first 8 characters
 */
public class IdGenerator {
    public static String generateUserId(String prefix) {
        String uniquePart = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
        return prefix + uniquePart;
    }
}
