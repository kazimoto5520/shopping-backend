package onlineshopping.pay.impl;

import lombok.RequiredArgsConstructor;
import onlineshopping.entity.Order;
import onlineshopping.exc.HandleExceptions;
import onlineshopping.model.PaymentRequest;
import onlineshopping.model.PaymentResponse;
import onlineshopping.pay.service.PaymentService;
import onlineshopping.repo.OrderRepo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AirtelMoneyServiceImpl implements PaymentService<PaymentRequest, PaymentResponse> {

    private final OrderRepo orderRepo;
    @Override
    public PaymentResponse pay(PaymentRequest request) {

        try {
            Double amount = request.getAmount();
            Order order = orderRepo.findByOrderNo(request.getOrderNo());

            // Airtel API to process payments
            /*
            if (airtelMoneyResponse.isSuccessful()){
              String transactionId = airtelMoneyResponse.getTransactionId();
        // Create successful Transaction object
        Transaction transaction = createTransaction(order, amount, TransactionType.PURCHASE, "Airtel Money", transactionId, "successful");
        // Save the transaction
        transactionRepository.save(transaction);
        return new PaymentResponse(true, transactionId);
      } else {
        // Extract error message
        String errorMessage = airtelMoneyResponse.getErrorMessage();
        return new PaymentResponse(false, errorMessage);
      }
    } catch (Exception e) {
      // Handle unexpected exceptions
      return new PaymentResponse(false, "Payment failed due to an error");
    }
  }
            }
            * */
            return null;
        }catch (HandleExceptions exceptions){
            throw new HandleExceptions(null);
        }
    }
}
