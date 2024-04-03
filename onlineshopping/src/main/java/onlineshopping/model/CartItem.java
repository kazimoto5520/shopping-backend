package onlineshopping.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartItem {
    private String itemNo;
    private int productQuantity;
    private String itemColor;
    private String itemSize;
}
