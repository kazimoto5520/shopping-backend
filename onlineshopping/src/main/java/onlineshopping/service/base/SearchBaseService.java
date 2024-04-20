package onlineshopping.service.base;

import onlineshopping.entity.Item;

import java.util.List;

public interface SearchBaseService {

    List<String> findItemNames(String queryStr);

    List<Item> findFoundItems();

    Item findUniqueItem(String queryString);
}
