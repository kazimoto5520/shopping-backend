package onlineshopping.contoller;

import lombok.RequiredArgsConstructor;
import onlineshopping.entity.Item;
import onlineshopping.exc.HandleExceptions;
import onlineshopping.exc.SearchExceptions;
import onlineshopping.model.ItemResponse;
import onlineshopping.service.impl.SearchServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchServiceImpl searchService;

    // auto completion drop-down
    @GetMapping("/item-options")
    public ResponseEntity<List<String>> itemProducts(){
        List<String> itemOptions = searchService.findAllItemNames();
        return ResponseEntity.ok(itemOptions);
    }


    // querying all items without passing any parameter, just automatically after the system loaded
    @GetMapping("/items")
    public ResponseEntity<List<Item>> findAllItems(){
        List<Item> items = searchService.findAllItems();
        return ResponseEntity.ok(items);
    }

    //querying specific item, with passed item number/id as a parameter
    @GetMapping("/item-product")
    public ResponseEntity<ItemResponse> findItem(@RequestParam String queryStr){
        Item queryItem = searchService.findUniqueItem(queryStr);
            if (queryItem != null){
                ItemResponse itemResponse = ItemResponse.builder()
                        .itemNo(queryItem.getItemNo())
                        .itemName(queryItem.getItemName())
                        .actualPrice(queryItem.getActualPrice())
                        .discountPrice(queryItem.getDiscountPrice())
                        .quantity(queryItem.getQuantity())
                        .description(queryItem.getDescription())
                        .ratings(queryItem.getRatings())
                        .imageUrl(queryItem.getImageUrl())
                        .sizes(queryItem.getSizes())
                        .colors(queryItem.getColors())
                        .build();
                return ResponseEntity.ok(itemResponse);
            }else {
                throw new SearchExceptions("No item found matching your search query.");
            }
    }
}
// fixme: searching all items using parameter
//fixme: search all items without passing any parameter
//fixme: search for autocompletion words by passing parameter
