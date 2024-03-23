package onlineshopping.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartItem {
    private Long productId;
    private String productName;
    private int productQuantity;
}
