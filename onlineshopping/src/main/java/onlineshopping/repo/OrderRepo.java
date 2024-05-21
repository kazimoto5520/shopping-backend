package onlineshopping.repo;

import onlineshopping.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {
    Order findByOrderNo(String orderNo);

    @Query("SELECT o.orderNo, u.name, u.email, o.totalPrice, s.order_status, o.date_created " +
           "FROM Order o " +
            "JOIN o.customer u " +
            "JOIN o.orderStatus s " +
            "ORDER BY o.date_created"
    )
    Page<Object[]> findOrders(Pageable pageable);

    @Query("SELECT o.orderNo, u.name, u.email, o.totalPrice, s.order_status, o.date_created " +
            "FROM Order o " +
            "JOIN o.customer u " +
            "JOIN o.orderStatus s " +
            "ORDER BY o.date_created DESC "
    )
    Page<Object[]> findLatestOrders(Pageable pageable);

    @Query("SELECT SUM(o.totalPrice) FROM Order o ")
    int findTotalSales();

    @Query("SELECT SUM(o.orderId) FROM Order o ")
    int findTotalOrders();

}
