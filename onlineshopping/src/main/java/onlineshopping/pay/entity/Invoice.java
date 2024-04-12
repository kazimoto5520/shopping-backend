package onlineshopping.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import onlineshopping.constants.PaymentStatus;

import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "invoice")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Invoice {
    @Id
    @SequenceGenerator(
            name = "invoice_sequence",
            sequenceName = "invoice_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "invoice_sequence"
    )
    private Long invoiceId;

    private String invoiceNUmber;

    private PaymentStatus status;

    @Column(name = "subtotal", nullable = false)
    private float subTotal;

    private LocalDate timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private Customer customer;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<Order> orders;

    /*@Column(name = "tax", nullable = false)
      private float taxAmount;

      @Column(name = "discount", nullable = false)
      private float discountAmount;

      @Column(name = "total_amount", nullable = false)
      private float totalAmount;*/

    @PrePersist
    public void onCreate(){
        timestamp = LocalDate.now();
    }

}
