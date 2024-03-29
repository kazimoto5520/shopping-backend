package onlineshopping.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import onlineshopping.constants.PaymentProviderType;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentRequest {
    private PaymentProviderType providerType;
    private String paymentId;
    private double amount;
}
