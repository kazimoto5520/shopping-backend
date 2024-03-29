package onlineshopping.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentResponse {
    private boolean success;
    private String message;
    private String transactionalID;
    private Map<String,String> additionalData;
}
