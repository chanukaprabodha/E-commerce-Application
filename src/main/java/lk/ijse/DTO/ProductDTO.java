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

    public ProductDTO(String productId, String name, String description, double price, String imageUrl) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}
