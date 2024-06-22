package onlineshopping.contoller;

import lombok.RequiredArgsConstructor;
import onlineshopping.exc.DatabaseAccessException;
import onlineshopping.exc.HandleExceptions;
import onlineshopping.model.PageResponse;
import onlineshopping.model.SalesPerMonthDTO;
import onlineshopping.service.impl.SearchServiceImpl;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
public class AdminController {

    private final SearchServiceImpl searchService;

    @CrossOrigin()
    @GetMapping("/all-users")
    public ResponseEntity<PageResponse<Object[]>> findAllUsers(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ){
        try {
            if (pageNumber < 0 || pageSize <= 0){
                throw new HandleExceptions("Invalid page number or size");
            }

            Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by("name","date_created"));
            Page<Object[]> customers = searchService.findAllUsers(pageable);

            PageResponse<Object[]> pageResponse = new PageResponse<>(
                    customers.getContent(),
                    customers.getTotalPages(),
                    customers.getNumberOfElements()
            );

            return ResponseEntity.ok(pageResponse);
        }catch (DataAccessException accessException){
            throw new DatabaseAccessException("Error: "+accessException.getMessage());
        }
    }

    @CrossOrigin()
    @GetMapping("/all-orders")
    public ResponseEntity<PageResponse<Object[]>> findOrders(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ){
        try {
            if (pageNumber < 0 || pageSize <= 0){
                throw new HandleExceptions("Invalid page number or size");
            }

            Pageable pageable = PageRequest.of(pageNumber,pageSize,Sort.by("date_created"));
            Page<Object[]> orders = searchService.findOrders(pageable);

            PageResponse<Object[]> pageResponse = new PageResponse<>(
                    orders.getContent(),
                    orders.getTotalPages(),
                    orders.getNumberOfElements()
            );

            return ResponseEntity.ok(pageResponse);
        }catch (DataAccessException accessException){
            throw new DatabaseAccessException("Error: "+accessException.getMessage());
        }
    }

    @CrossOrigin()
    @GetMapping("/latest-orders")
    public ResponseEntity<PageResponse<Object[]>> findLatestOrders(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "3") int pageSize
    ){
        try {
            if (pageNumber < 0 || pageSize <= 0){
                throw new HandleExceptions("Invalid page number or size");
            }

            Pageable pageable = PageRequest.of(pageNumber,pageSize,Sort.by("date_created").descending());
            Page<Object[]> orders = searchService.findLatestOrders(pageable);

            PageResponse<Object[]> pageResponse = new PageResponse<>(
                    orders.getContent(),
                    orders.getTotalPages(),
                    orders.getNumberOfElements()
            );

            return ResponseEntity.ok(pageResponse);
        }catch (DataAccessException accessException){
            throw new DatabaseAccessException("Error: "+accessException.getMessage());
        }
    }

    @CrossOrigin()
    @GetMapping("/total-sales")
    public ResponseEntity<Integer> findTotalSales() {
        int totalSales = searchService.findTotalSales();
        return ResponseEntity.ok(totalSales);
    }

    @CrossOrigin()
    @GetMapping("/total-orders")
    public ResponseEntity<Integer> findTotalOrders(){
        int totalOrders = searchService.findTotalOrders();
        return ResponseEntity.ok(totalOrders);
    }

    @CrossOrigin()
    @GetMapping("/total-ordered-products")
    public ResponseEntity<Integer> findTotalProduct(){
        int totalProduct = searchService.findTotalProduct();
        return ResponseEntity.ok(totalProduct);
    }

    @CrossOrigin()
    @GetMapping("/product")
    public ResponseEntity<PageResponse<Object[]>> findProducts(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ){
        try {
            if (pageNumber < 0 || pageSize <= 0){
                throw new HandleExceptions("Invalid page number or size");
            }

            Pageable pageable = PageRequest.of(pageNumber,pageSize,Sort.by(""));
            Page<Object[]> products = searchService.findProducts(pageable);

            PageResponse<Object[]> pageResponse = new PageResponse<>(
                    products.getContent(),
                    products.getTotalPages(),
                    products.getNumberOfElements()
            );
            return ResponseEntity.ok(pageResponse);
        }catch (DataAccessException accessException){
            throw new DatabaseAccessException("Error: "+accessException.getMessage());
        }
    }

    @CrossOrigin()
    @GetMapping("/sales-per-month")
    public ResponseEntity<List<SalesPerMonthDTO>> findSalesPerMonth() {
        List<SalesPerMonthDTO> salesPerMonthList = searchService.getSalesPerMonth();
        return ResponseEntity.ok(salesPerMonthList);
    }
}
//payment details: customer name, payment schedule, Bill number, Amount paid, balance amount, date
//sales for month