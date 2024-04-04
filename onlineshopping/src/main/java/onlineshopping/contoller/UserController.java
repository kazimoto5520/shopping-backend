package onlineshopping.contoller;

import lombok.RequiredArgsConstructor;
import onlineshopping.model.CartItem;
import onlineshopping.model.OrderRequest;
import onlineshopping.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(path = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/cart/checkout")
    public ResponseEntity<String> placeOrder(
            @RequestBody OrderRequest orderRequest
    ) {
        try {

            if (orderRequest.getEmail().isEmpty() || orderRequest.getStreet().isEmpty() || orderRequest.getRegion().isEmpty()) {

                return ResponseEntity.badRequest().body("Please fill in all address fields!");
            }
            else if (orderRequest.getCartItems() == null || orderRequest.getCartItems().isEmpty()) {

                return ResponseEntity.badRequest().body("Cart is empty!");
            }

            List<String> processingResults = new ArrayList<>(); // Track processing results

            for (CartItem item : orderRequest.getCartItems()) {
                try {
                    userService.processOrder(orderRequest.getEmail(), orderRequest.getStreet(), orderRequest.getRegion(), item);
                    processingResults.add("Successfully processed item " + item.getItemNo());
                } catch (Exception e) {
                    processingResults.add("Failed to process item " + item.getItemNo() + ": " + e.getMessage());
                }
            }

            String responseMessage;
            if (processingResults.stream().allMatch(result -> result.startsWith("Successfully"))) {
                responseMessage = "Successfully processed all items in your cart!";
            } else {
                responseMessage = "Encountered errors while processing some items:\n" + String.join("\n", processingResults);
            }

            return ResponseEntity.ok(responseMessage);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to process your order!");
        }
    }
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

}
