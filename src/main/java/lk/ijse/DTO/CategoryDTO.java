package lk.ijse.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: Chanuka Prabodha
 * Date: 2025-01-24
 * Time: 09:41 AM
 * Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Data

public class CategoryDTO {
    private String categoryId;
    private String name;
    private String description;
}
