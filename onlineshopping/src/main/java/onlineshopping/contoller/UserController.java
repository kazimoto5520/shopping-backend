package onlineshopping.contoller;

import lombok.RequiredArgsConstructor;
import onlineshopping.entity.CartItem;
import onlineshopping.model.OrderRequest;
import onlineshopping.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(path = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/cart/checkout")
    public ResponseEntity<String> placeOrder(
            @RequestBody OrderRequest orderRequest
            ){
        if (orderRequest.getEmail().isEmpty() || orderRequest.getStreet().isEmpty() || orderRequest.getRegion().isEmpty()) {
            return ResponseEntity.badRequest().body("Please fill in all address fields!");
        }

        if (orderRequest.getCartItems() == null || orderRequest.getCartItems().isEmpty()) {
            return ResponseEntity.badRequest().body("Cart is empty!");
        }

        try {
            for (CartItem item : orderRequest.getCartItems()) {
                userService.processOrder(orderRequest.getEmail(), orderRequest.getStreet(), orderRequest.getRegion(), item);
            }
            return ResponseEntity.ok("Orders placed successfully! We will deliver in no time");
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to process one or more orders!");
        }
    }

}
