package onlineshopping.pay;

import lombok.RequiredArgsConstructor;
import onlineshopping.constants.PaymentMethod;
import onlineshopping.model.PaymentRequest;
import onlineshopping.model.PaymentResponse;
import onlineshopping.pay.impl.AirtelMoneyServiceImpl;
import onlineshopping.pay.impl.GePGServiceImpl;
import onlineshopping.pay.impl.NmbCardService;
import onlineshopping.pay.impl.TigoPesaServiceImpl;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentFacade {

    private final AirtelMoneyServiceImpl airtelMoneyService;
    private final TigoPesaServiceImpl tigoPesaService;
    private final GePGServiceImpl gePGService;
    private final NmbCardService nmbCardService;

    public PaymentResponse pay(PaymentRequest request){
        PaymentMethod providerType = request.getProviderType();
        switch (providerType){
            case GEPG :
                return gePGService.pay(request);
            case TIGO_PESA:
                return tigoPesaService.pay(request);
            case AIRTEL_MONEY:
                return airtelMoneyService.pay(request);
            case NMB_CARD:
                return nmbCardService.pay(request);
            default:
                return null;
        }
    }
}
