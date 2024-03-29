package onlineshopping.pay;

import lombok.RequiredArgsConstructor;
import onlineshopping.constants.PaymentProviderType;
import onlineshopping.model.PaymentRequest;
import onlineshopping.model.PaymentResponse;
import onlineshopping.pay.impl.AirtelMoneyServiceImpl;
import onlineshopping.pay.impl.GePGServiceImpl;
import onlineshopping.pay.impl.TigoPesaServiceImpl;
import org.springframework.stereotype.Component;

import static onlineshopping.constants.PaymentProviderType.*;

@Component
@RequiredArgsConstructor
public class PaymentFacade {

    private final AirtelMoneyServiceImpl airtelMoneyService;
    private final TigoPesaServiceImpl tigoPesaService;
    private final GePGServiceImpl gePGService;

    public PaymentResponse pay(PaymentRequest request){
        PaymentProviderType providerType = request.getProviderType();
        switch (providerType){
            case GEPG :
                return gePGService.pay(request);
            case TIGO_PESA:
                return tigoPesaService.pay(request);
            case AIRTEL_MONEY:
                return airtelMoneyService.pay(request);
            default:
                return null;
        }
    }
}
