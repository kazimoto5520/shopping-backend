package onlineshopping.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import onlineshopping.constants.UserRole;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Users {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long userId;
    private String name;
    private String email;
    private String mobile;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private LocalDate date_created;// when an account created

    @PrePersist
    public void onCreate(){
        date_created = LocalDate.now();
    }

}
