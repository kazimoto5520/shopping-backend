package onlineshopping.contoller;

import lombok.RequiredArgsConstructor;
import onlineshopping.entity.Order;
import onlineshopping.model.*;
import onlineshopping.pay.PaymentFacade;
import onlineshopping.service.impl.OrderServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@CrossOrigin()
@RestController
@RequestMapping(path = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final OrderServiceImpl userService;
    private final PaymentFacade paymentFacade;

    @CrossOrigin()
    @PostMapping("/cart/checkout")
    public ResponseEntity<OrderResponse> placeOrder(
            @RequestBody OrderRequest orderRequest
    ) {
        OrderResponse response = new OrderResponse();

        if (orderRequest.getEmail().isEmpty() || orderRequest.getStreet().isEmpty() || orderRequest.getRegion().isEmpty()) {
            response.setErrorMessage("Please fill in all address fields!");
            return ResponseEntity.badRequest().body(response);
        } else if (orderRequest.getCartItems() == null || orderRequest.getCartItems().isEmpty()) {
            response.setErrorMessage("Cart is empty!");
            return ResponseEntity.badRequest().body(response);
        }

        for (CartItem item : orderRequest.getCartItems()) {
            try {
               Order order= userService.processOrder(orderRequest.getEmail(), orderRequest.getStreet(), orderRequest.getRegion(), item).getBody();
                assert order != null;
                response.setOrderNo(order.getOrderNo());
                response.setCustomerEmail(order.getCustomer().getEmail());
                response.setBilling_address(order.getAddress());
            } catch (Exception e) {
                response.setSuccessful(false);
                String itemErrorMessage = String.format("Failed to process item %s: %s", item.getItemNo(), e.getMessage());
                response.setErrorMessage((response.getErrorMessage() == null ? "" : response.getErrorMessage() + "\n") + itemErrorMessage);
            }
        }

        response.setSuccessful(response.getErrorMessage() == null);
        if (response.isSuccessful()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @CrossOrigin()
    @PostMapping("/publish-product")
    public ResponseEntity<String> publishItem(
            @RequestParam(name = "itemName", required = false) String itemName,
            @RequestParam(name = "sizes", required = false) List<String> sizes,
            @RequestParam(name = "colors", required = false) List<String> colors,
            @RequestParam(name = "stokeQuantity", required = false) int stokeQuantity,
            @RequestParam(name = "actualPrice", required = false) float actualPrice,
            @RequestParam(name = "discountPrice", required = false) float discountPrice,
            @RequestParam(name = "description", required = false) String description,
            @RequestParam(name = "imageUrl", required = false) MultipartFile imageUrl
            ){
        return userService.publishItem(
                itemName,sizes,colors,
                stokeQuantity,actualPrice,discountPrice,description,imageUrl
        );
    }

    @CrossOrigin()
    @PostMapping("/payments")
    public ResponseEntity<PaymentResponse> makePayment(@RequestBody PaymentRequest paymentRequest)
    {
       return ResponseEntity.ok(paymentFacade.pay(paymentRequest));
    }

}
