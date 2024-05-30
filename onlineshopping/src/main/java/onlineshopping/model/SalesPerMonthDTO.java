package onlineshopping.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SalesPerMonthDTO {
    private String month;
    private double totalSales;
}
