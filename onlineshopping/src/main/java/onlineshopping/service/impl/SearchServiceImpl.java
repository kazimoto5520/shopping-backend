package onlineshopping.service.impl;

import lombok.RequiredArgsConstructor;
import onlineshopping.entity.Item;
import onlineshopping.exc.HandleExceptions;
import onlineshopping.repo.*;
import onlineshopping.service.base.SearchBaseService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchBaseService {
    private final UserRepo userRepo;
    private final OrderRepo orderRepo;
    private final OrderStatusRepo orderStatusRepo;
    private final OrderItemRepo orderItemRepo;
    private final ItemRepo itemRepo;

    @Override
    public List<String> findAllItemNames() {
       try {
           return itemRepo.findAllItems();
       }catch (DataAccessException exception){
           throw new HandleExceptions("Error: No search result found");
       }
    }

    @Override
    public List<Item> findAllItems(String itemQuery) {
        try {
            return itemRepo.findByItemNameContainingIgnoreCase(itemQuery);
        }catch (DataAccessException exception){
            throw new HandleExceptions("Error: No search result found");
        }
    }

    @Override
    public Item findUniqueItem(String queryString) {
        try {
            return itemRepo.findByItemNameIgnoreCase(queryString);

        }catch (DataAccessException exception){
            throw new HandleExceptions("Error: No search result found");
        }
    }

}
