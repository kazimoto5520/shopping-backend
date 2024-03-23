package onlineshopping.contoller;

import lombok.RequiredArgsConstructor;
import onlineshopping.entity.CartItem;
import onlineshopping.service.impl.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/cart/checkout")
    public ResponseEntity<String> placeOrder(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "street") String street,
            @RequestParam(name = "region") String region,
            @RequestParam(name = "cartItem") List<CartItem> cartItems
    ){
        if (email.isEmpty() || street.isEmpty() || region.isEmpty()) {
            return ResponseEntity.badRequest().body("Please fill in all address fields!");
        }

        if (cartItems == null || cartItems.isEmpty()) {
            return ResponseEntity.badRequest().body("Cart is empty!");
        }

        for (CartItem item : cartItems) {
            return userService.processOrder(email, street, region, item);
        }

        return ResponseEntity.internalServerError().body("Error occurred while processing an order!");
    }

}
