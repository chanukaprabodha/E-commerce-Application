package lk.ijse.DTO;

import lk.ijse.Entity.User;
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
    private String password;
    private String email;
    private User.Role role;
    private boolean active;
}
