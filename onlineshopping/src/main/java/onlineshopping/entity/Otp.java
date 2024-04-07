package onlineshopping.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "otp_codes")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Otp {
    @Id
    @SequenceGenerator(
            name = "otp_sequence",
            sequenceName = "otp_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "otp_sequence"
    )
    private Long otpId;

    @Column(name = "otp", nullable = false)
    private String otpCode;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    private LocalDateTime createdAt;
}
