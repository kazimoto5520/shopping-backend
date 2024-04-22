package onlineshopping.repo;

import onlineshopping.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepo extends JpaRepository<Item, Long> {
    Item findByItemNo(String itemNo);

    @Query("SELECT LOWER(i.itemName) FROM Item i WHERE LOWER(i.itemName) LIKE LOWER(CONCAT('%', :queryStr, '%')) ORDER BY LOWER(i.itemName)")
    List<String> findItemNames(@Param("queryStr") String queryStr);

    @Query("SELECT i FROM Item i " +
            "LEFT JOIN i.sizes s " +
            "LEFT JOIN i.colors c " +
            "ORDER BY i.itemName")
    List<Item> findAllItem();

    @Query("SELECT i.imageUrl FROM Item i WHERE i.imageUrl LIKE :imageName")
    Optional<String> findByImageUrl(@Param("imageName") String imageName);
}
