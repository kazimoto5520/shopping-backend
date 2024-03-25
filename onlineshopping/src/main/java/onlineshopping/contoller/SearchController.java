package onlineshopping.contoller;

import lombok.RequiredArgsConstructor;
import onlineshopping.entity.Item;
import onlineshopping.service.impl.SearchServiceImpl;
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

    @GetMapping("/items")
    public ResponseEntity<List<Item>> findAllItems(@RequestParam String itemQuery){
        List<Item> items = searchService.findAllItems(itemQuery);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/item-product")
    public ResponseEntity<Item> findItem(@RequestParam String queryString){
        Item queryItem = searchService.findUniqueItem(queryString);
        return ResponseEntity.ok(queryItem);
    }
}
//auto compli
//item zote
//exactly
//