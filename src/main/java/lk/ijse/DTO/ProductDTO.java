package lk.ijse.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: Chanuka Prabodha
 * Date: 2025-01-25
 * Time: 09:33 PM
 * Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Data

public class ProductDTO {
    private String productId;
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private String categoryId;
}
