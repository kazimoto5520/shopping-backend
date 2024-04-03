package onlineshopping.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "items")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Item {
    @Id
    @SequenceGenerator(
            name = "item_sequence",
            sequenceName = "item_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "item_sequence"
    )
    private Long itemId;


    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "item_number", nullable = false)
    private String itemNo;

    @Column(name = "price", nullable = false)
    private float actual_price;

    @Column(name = "discount")
    private float discount_price;

    private int quantity;// available stoke quantity of the products/items
    private String description;//product description
    private int ratings;

    @Column(name = "images")
    private String imageUrl;// url for product images

    @ElementCollection
    @CollectionTable(name = "item_sizes", joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "size")
    private List<String> sizes; // List of sizes for the item

    @ElementCollection
    @CollectionTable(name = "item_colors", joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "color")
    private List<String> colors; // List of colors for the item
}
