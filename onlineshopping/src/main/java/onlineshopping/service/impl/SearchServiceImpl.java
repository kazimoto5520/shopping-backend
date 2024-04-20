package onlineshopping.service.impl;

import lombok.RequiredArgsConstructor;
import onlineshopping.entity.Item;
import onlineshopping.exc.HandleExceptions;
import onlineshopping.repo.*;
import onlineshopping.service.base.SearchBaseService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchBaseService {
    private final UserRepo userRepo;
    private final OrderRepo orderRepo;
    private final OrderStatusRepo orderStatusRepo;
    private final OrderItemRepo orderItemRepo;
    private final ItemRepo itemRepo;

    @Override
    public List<String> findItemNames(String queryStr) {
       try {
           return itemRepo.findItemNames(queryStr);
       }catch (DataAccessException exception){
           throw new HandleExceptions("Error: No search result found");
       }
    }


    @Override
    public List<Item> findFoundItems() {
        try {
            return itemRepo.findAllItem();
        }catch (DataAccessException exception){
            throw new HandleExceptions("Error: "+exception.getMessage());
        }
    }


    @Override
    public Item findUniqueItem(String queryString) {
        try {
            return itemRepo.findByItemNo(queryString);
        } catch (NoSuchElementException e) {
            throw new HandleExceptions("No item found matching your search query.");
        }
    }

}
