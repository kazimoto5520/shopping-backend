package onlineshopping.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PageResponse<T>{
    private final List<T> content;
    private final int totalPages;
    private final int totalElements;

}
