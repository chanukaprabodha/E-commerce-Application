package lk.ijse.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: Chanuka Prabodha
 * Date: 2025-01-26
 * Time: 09:49 AM
 * Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO {
    private String orderId;
    private String customerId;
    private String orderDate;
    private double totalAmount;
    private String status;
}
