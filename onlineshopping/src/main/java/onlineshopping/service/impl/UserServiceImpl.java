package onlineshopping.service.impl;

import lombok.RequiredArgsConstructor;
import onlineshopping.constants.Status;
import onlineshopping.entity.*;
import onlineshopping.exc.HandleExceptions;
import onlineshopping.repo.*;
import onlineshopping.service.base.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final OrderRepo orderRepo;
    private final OrderItemRepo orderItemRepo;
    private final OrderStatusRepo statusRepo;
    private final ItemRepo itemRepo;


    @Override
    public ResponseEntity<String> processOrder(String email, String street, String region, CartItem item) {
        try {
            User user = userRepo.findByEmail(email);
            if (user == null){
                throw new HandleExceptions("Oops! you need to have an account");
            }

            Order order = new Order();
            order.setOrderNo(generateRandomOrderNumber());
            order.setAddress(street + " " + region);
            order.setUser(user);
            orderRepo.save(order);

            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setOrder_status(Status.ongoing.name());
            orderStatus.setOrder(order);
            statusRepo.save(orderStatus);

            if (item != null){
                saveOrderItem(order, item.getProductName(), item.getProductQuantity());
            }

            return ResponseEntity.ok("Order successfully! we will deliver in no time");
        }catch (Exception ignored){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to process your order!");
        }
    }

    private void saveOrderItem(Order order, String productName, int productQuantity) {
        try {
            Item item = itemRepo.findByItemName(productName);
            if (item != null){
                OrderItem orderItem = new OrderItem();
                orderItem.setItem(item);
                orderItem.setQuantity(productQuantity);
                orderItem.setOrder(order);
                orderItemRepo.save(orderItem);

                item.setQuantity(item.getQuantity() - 1);
                itemRepo.save(item);
            }
        }catch (Exception exception){
            throw new HandleExceptions("Oops! can't find that item");
        }
    }

    private String generateRandomOrderNumber(){
        int orderNumberLength = 5;
        StringBuilder builder = new StringBuilder();

        Random random = new Random();
        for (int i=0; i < orderNumberLength; i++){
            int digit = random.nextInt(10);
            builder.append(digit);
        }
        return "555"+builder;
    }
}
