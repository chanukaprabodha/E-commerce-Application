package lk.ijse.Entity;

/**
 * Author: Chanuka Prabodha
 * Date: 2025-01-21
 * Time: 09:58 PM
 * Description:
 */

public class User {
    private String userID;
    private String userName;
    private String password;
    private String email;
    private Role role;
    private boolean active;

    public enum Role {
        ADMIN,
        CUSTOMER
    }
}
