package onlineshopping.repo;

import onlineshopping.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepo extends JpaRepository<Item, Long> {
    Item findByItemNo(String itemNo);

    @Query("SELECT DISTINCT i.itemName FROM Item i"
    )
    List<String> findAllItems();

    @Query("SELECT i.itemName, i.itemNo, i.actualPrice, i.discountPrice, i.quantity, i.description, i.ratings, i.imageUrl, i.sizes, i.colors " +
       "FROM Item i " +
       "ORDER BY i.itemName"
    )
    List<Item> findAllItem();
}
