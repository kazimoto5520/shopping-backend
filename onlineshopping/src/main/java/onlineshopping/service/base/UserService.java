package onlineshopping.service.base;

import onlineshopping.entity.CartItem;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<String> processOrder(String email, String street, String region, CartItem item);
}
