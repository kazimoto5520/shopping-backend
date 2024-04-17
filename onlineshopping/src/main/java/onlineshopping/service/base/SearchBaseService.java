package onlineshopping.service.base;

import onlineshopping.entity.Item;

import java.util.List;

public interface SearchBaseService {

    List<String> findAllItemNames();

    List<Item> findAllItems();

    Item findUniqueItem(String queryString);
}
