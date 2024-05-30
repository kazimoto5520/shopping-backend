package onlineshopping.service.base;

import onlineshopping.entity.Item;
import onlineshopping.model.SalesPerMonthDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SearchBaseService {

    List<String> findItemNames(String queryStr);

    List<Item> findFoundItems();

    Item findUniqueItem(String queryString);

    Page<Object[]> findAllUsers(Pageable pageable);

    Page<Object[]> findOrders(Pageable pageable);

    Page<Object[]> findLatestOrders(Pageable pageable);

    int findTotalSales();

    int findTotalOrders();

    int findTotalProduct();

    Page<Object[]> findProducts(Pageable pageable);

    List<SalesPerMonthDTO> getSalesPerMonth();
}
