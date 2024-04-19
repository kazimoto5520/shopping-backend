package onlineshopping.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ItemResponse {
    private String itemName;
    private String itemNo;
    private float actualPrice;
    private float discountPrice;
    private int quantity;
    private String description;
    private int ratings;
    private String imageUrl;
    private List<String> sizes;
    private List<String> colors;
}
