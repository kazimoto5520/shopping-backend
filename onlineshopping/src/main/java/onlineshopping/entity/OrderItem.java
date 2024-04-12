package onlineshopping.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "order_item")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderItem {
    @Id
    @SequenceGenerator(
            name = "order_item_sequence",
            sequenceName = "order_item_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_item_sequence"
    )
    private Long orderItemId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", referencedColumnName = "orderId")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "itemId")
    private Item item;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @ElementCollection
    @CollectionTable(name = "order_item_sizes", joinColumns = @JoinColumn(name = "order_item_id"))
    @Column(name = "size")
    private List<String> sizes; // List of sizes for the item

    @ElementCollection
    @CollectionTable(name = "order_item_colors", joinColumns = @JoinColumn(name = "order_item_id"))
    @Column(name = "color")
    private List<String> colors; // List of colors for the item
}
