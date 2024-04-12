package onlineshopping.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import onlineshopping.entity.Order;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderResponse {
    private String orderNo;
    private String customerEmail;
    private String billing_address;
    private String errorMessage;
    private boolean successful;
}
