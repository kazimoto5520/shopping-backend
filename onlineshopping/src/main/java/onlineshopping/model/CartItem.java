package onlineshopping.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartItem {
    private String itemNo;
    private int productQuantity;
    private List<String> sizes;
    private List<String> colors;
}
