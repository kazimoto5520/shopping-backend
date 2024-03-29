package onlineshopping.pay.service;

import onlineshopping.model.PaymentRequest;
import onlineshopping.model.PaymentResponse;

public interface PaymentService<T extends PaymentRequest, R extends PaymentResponse> {
    R pay(T request);
}
