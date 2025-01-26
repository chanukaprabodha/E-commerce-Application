package lk.ijse.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: Chanuka Prabodha
 * Date: 2025-01-21
 * Time: 10:09 PM
 * Description:
 */

@AllArgsConstructor
@NoArgsConstructor
@Data

public class UserDTO {

    private String userID;
    private String userName;
    private String email;
    private String Role;
    private boolean active;

    public boolean isActive() {
        return active;
    }
}
