package onlineshopping.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ItemFacade {
    private String itemName;
    private List<String> sizes;
    private List<String> colors;
    private int stokeQuantity;
    private float actualPrice;
    private float discountPrice;
    private String description;
    private MultipartFile imageUrl;
}
