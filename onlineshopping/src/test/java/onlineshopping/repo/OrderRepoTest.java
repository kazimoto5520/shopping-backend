package onlineshopping.repo;

import onlineshopping.entity.CartItem;
import onlineshopping.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class OrderRepoTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void placeOrder(){
        String email = "muddyfakih98@gmail.com";
        String street = "kinondoni";
        String region = "Dar Es Salaam";
        CartItem cartItem = new CartItem("Product X", 5);

        ResponseEntity<String> testOrder = userService.processOrder(email,street,region,cartItem);
        assertEquals(HttpStatus.OK, testOrder.getStatusCode());
        assertEquals("Order successfully! we will deliver in no time",testOrder.getBody());

    }



}