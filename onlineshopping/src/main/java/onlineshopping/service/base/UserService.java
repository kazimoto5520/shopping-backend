package onlineshopping.service.base;

import onlineshopping.entity.CartItem;
import onlineshopping.model.ItemFacade;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<String> processOrder(String email, String street, String region, CartItem item);

    ResponseEntity<String> publishItem(ItemFacade itemFacade);
}
