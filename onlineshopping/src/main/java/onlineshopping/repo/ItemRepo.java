package onlineshopping.repo;

import onlineshopping.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepo extends JpaRepository<Item, Long> {
    Item findByItemNo(String itemNo);

}
