package onlineshopping.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import onlineshopping.pay.entity.Invoice;
import onlineshopping.pay.entity.Transaction;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Order {
    @Id
    @SequenceGenerator(
            name = "order_sequence",
            sequenceName = "order_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_sequence"
    )
    private Long orderId;

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",referencedColumnName = "userId")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderItem> orderItems;

    @OneToOne(mappedBy = "order",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Transaction> transaction;

    @ManyToOne
    @JoinColumn(name = "invoice_id", referencedColumnName = "invoiceId")
    private Invoice invoice;

    @Column(name = "order_number",nullable = false)
    private String orderNo;

    @Column(name = "billing_address",nullable = false)
    private String address;

    private LocalDate date_created;

    private LocalDate date_updated;

    @PrePersist
    public void onCreate(){
        date_created = LocalDate.now();
    }

    @PreUpdate
    public void onUpdate(){
        date_updated = LocalDate.now();
    }

}
