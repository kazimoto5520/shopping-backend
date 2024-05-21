package onlineshopping.service.impl;

import lombok.RequiredArgsConstructor;
import onlineshopping.entity.Item;
import onlineshopping.exc.DatabaseAccessException;
import onlineshopping.exc.HandleExceptions;
import onlineshopping.repo.*;
import onlineshopping.service.base.SearchBaseService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchBaseService {

    private final ItemRepo itemRepo;
    private final UserRepo userRepo;
    private final OrderRepo orderRepo;
    private final OrderItemRepo orderItemRepo;
    private final OrderStatusRepo statusRepo;

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
        List<Item> items = itemRepo.findAllItem();
        for (Item item : items) {
            // Process sizes and colors to remove escaped quotes
            List<String> processedSizes = item.getSizes().stream()
                    .map(size -> size.replaceAll("^\"|\"$", ""))
                    .collect(Collectors.toList());
            item.setSizes(processedSizes);

            List<String> processedColors = item.getColors().stream()
                    .map(color -> color.replaceAll("^\"|\"$", ""))
                    .collect(Collectors.toList());
            item.setColors(processedColors);
        }
        return items;
    }


    @Override
    public Item findUniqueItem(String queryString) {
        try {
            return itemRepo.findByItemNo(queryString);
        } catch (NoSuchElementException e) {
            throw new HandleExceptions("No item found matching your search query.");
        }
    }

    @Override
    public Page<Object[]> findAllUsers(Pageable pageable) {
        try {
            return userRepo.findAllUsers(pageable);
        }catch (DataAccessException accessException){
            throw new DatabaseAccessException("Error: "+accessException.getMessage());
        }
    }

    @Override
    public Page<Object[]> findOrders(Pageable pageable) {
        try {
            return orderRepo.findOrders(pageable);
        }catch (DataAccessException accessException){
            throw new DatabaseAccessException("Error: "+accessException.getMessage());
        }
    }

    @Override
    public Page<Object[]> findLatestOrders(Pageable pageable) {
        try {
            return orderRepo.findLatestOrders(pageable);
        }catch (DataAccessException accessException){
            throw new DatabaseAccessException("Error: "+accessException.getMessage());
        }
    }

    @Override
    public int findTotalSales() {
        try {
           return orderRepo.findTotalSales();
        }catch (DataAccessException accessException){
            throw new DatabaseAccessException("Error: "+accessException.getMessage());
        }
    }

    @Override
    public int findTotalOrders() {
        try {
            return orderRepo.findTotalOrders();
        }catch (DataAccessException accessException){
            throw new DatabaseAccessException("Error: "+accessException.getMessage());
        }
    }

    @Override
    public int findTotalProduct() {
        try {
            return orderItemRepo.findTotalProduct();
        }catch (DataAccessException accessException){
            throw new DatabaseAccessException("Error: "+accessException.getMessage());
        }
    }

    @Override
    public Page<Object[]> findProducts(Pageable pageable) {
        try {
            return orderItemRepo.findProducts(pageable);
        }catch (DataAccessException accessException){
            throw new DatabaseAccessException("Error: "+accessException);
        }
    }


    public ResponseEntity<String> getImagePath(String imageName){
        Optional<String> image_path = itemRepo.findByImageUrl(imageName);
        if (image_path.isPresent()){
            String imagePath = "/images/" + imageName;
            return ResponseEntity.status(HttpStatus.FOUND).body(imagePath);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Oops! image not found");
        }
    }

}
