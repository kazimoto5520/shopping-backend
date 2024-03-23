package onlineshopping.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "order_status")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderStatus {
    @Id
    @SequenceGenerator(
            name = "order_status_sequence",
            sequenceName = "order_status_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_status_sequence"
    )
    private Long order_status_Id;

    @OneToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id",referencedColumnName = "orderId")
    private Order order;

    private String order_status;

    private LocalDate date_created;

    private LocalDate date_updated;// date for its status to change

    @PrePersist
    public void onCreate(){
        date_created = LocalDate.now();
    }

    @PreUpdate
    public void onUpdate(){
        date_updated = LocalDate.now();
    }

}
