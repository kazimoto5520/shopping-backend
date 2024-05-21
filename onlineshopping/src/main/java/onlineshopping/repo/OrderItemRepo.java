package onlineshopping.repo;

import onlineshopping.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem,Long> {

    @Query("SELECT SUM(oi.quantity) FROM OrderItem oi")
    int findTotalProduct();

    @Query("SELECT i.itemName, i.actualPrice, oi.order.date_created, i.datePublished " +
            "FROM OrderItem oi " +
            "JOIN oi.item i " +
            "JOIN oi.order o " +
            "ORDER BY o.date_created DESC")
    Page<Object[]> findProducts(Pageable pageable);

}
