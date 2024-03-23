package onlineshopping.repo;

import onlineshopping.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusRepo extends JpaRepository<OrderStatus,Long> {
}
