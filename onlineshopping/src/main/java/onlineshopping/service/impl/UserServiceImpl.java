package onlineshopping.service.impl;

import lombok.RequiredArgsConstructor;
import onlineshopping.constants.Status;
import onlineshopping.entity.*;
import onlineshopping.exc.HandleExceptions;
import onlineshopping.model.CartItem;
import onlineshopping.model.ItemFacade;
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
            else {
                Item items = itemRepo.findByItemNo(item.getItemNo());
                if (items == null){
                    throw new HandleExceptions("Oops! Can't find that item");
                }
                else {
                    Order order = new Order();
                    order.setOrderNo(generateRandomOrderNumber());
                    order.setAddress(street + " " + region);
                    order.setUser(user);
                    orderRepo.save(order);

                    OrderStatus orderStatus = new OrderStatus();
                    orderStatus.setOrder_status(Status.ongoing.name());
                    orderStatus.setOrder(order);
                    statusRepo.save(orderStatus);

                    saveOrderItem(order, item.getItemNo(), item.getProductQuantity());

                    return ResponseEntity.ok("Order successfully! we will deliver in no time");
                }
            }
        }catch (HandleExceptions exception){

            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception exception){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to process your order!");
        }
    }

    @Override
    public ResponseEntity<String> publishItem(ItemFacade itemFacade) {
        try {
            String item_no = generateRandomAlphanumericItemNo();

            Item item = getItem(itemFacade, item_no);

            itemRepo.save(item);
            return ResponseEntity.ok("publishing successfully");

        }catch (HandleExceptions exception){
            throw new HandleExceptions("Something went wrong while processing publication of item: "+ exception);
        }
    }

    private static Item getItem(ItemFacade itemFacade, String item_no) {
        Item item = new Item();
        item.setItemNo(item_no);
        item.setItemName(itemFacade.getItemName());
        item.setActual_price(itemFacade.getActualPrice());
        item.setQuantity(itemFacade.getStokeQuantity());
        item.setDescription(itemFacade.getDescription());
        item.setDiscount_price(itemFacade.getDiscountPrice());
        item.setImageUrl(itemFacade.getImageUrl());
        item.setRatings(0);// Default each product/item has 0 ratings
        item.setColors(itemFacade.getColors());
        item.setSizes(itemFacade.getSizes());
        return item;
    }

    private void saveOrderItem(Order order, String itemNo, int productQuantity) {
        Item item = itemRepo.findByItemNo(itemNo);
        if (item != null) {
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setQuantity(productQuantity);
            orderItem.setOrder(order);
            orderItemRepo.save(orderItem);

            item.setQuantity(item.getQuantity() - 1);
            itemRepo.save(item);
        } else {
            throw new HandleExceptions("Oops! invalid or not exist item number");
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

    private String generateRandomAlphanumericItemNo() {
        StringBuilder randomAlphanumericItemNo = new StringBuilder(5);

        String alphanumeric = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        Random random = new Random();

        for (int i = 0; i < 15; i++) {
            int index = random.nextInt(alphanumeric.length());
            char randomChar = alphanumeric.charAt(index);
            randomAlphanumericItemNo.append(randomChar);
        }

        return randomAlphanumericItemNo.toString();
    }
}
