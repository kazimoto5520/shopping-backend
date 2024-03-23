package onlineshopping.repo;

import onlineshopping.entity.Item;
import onlineshopping.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    Item findByItemName(String productName);
}
