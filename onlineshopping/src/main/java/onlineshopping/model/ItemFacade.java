package onlineshopping.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ItemFacade {
    private String item_name;
    private int stoke_quantity;
    private float actual_price;
    private float discount_price;
    private String description;
    private String imageUrl;
}
