package onlineshopping.pay.impl;

import lombok.RequiredArgsConstructor;
import onlineshopping.model.PaymentRequest;
import onlineshopping.model.PaymentResponse;
import onlineshopping.pay.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TigoPesaServiceImpl implements PaymentService<PaymentRequest, PaymentResponse> {
    @Override
    public PaymentResponse pay(PaymentRequest request) {
        return null;
    }
}
