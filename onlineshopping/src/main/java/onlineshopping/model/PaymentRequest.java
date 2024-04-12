package onlineshopping.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import onlineshopping.constants.PaymentMethod;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentRequest {
    @Enumerated(EnumType.STRING)
    private PaymentMethod providerType;

    private String orderNo;
    private String paymentId;
    private double amount;
    //phone number or card number based on provider
}
