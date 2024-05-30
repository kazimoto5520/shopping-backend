package onlineshopping.repo;

import onlineshopping.pay.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction,Long> {

    @Query("SELECT TO_CHAR(t.timestamp, 'YYYY-MM') as month, SUM(t.amount) as totalSales " +
            "FROM Transaction t " +
            "GROUP BY TO_CHAR(t.timestamp, 'YYYY-MM') " +
            "ORDER BY month")
    List<Object[]> findSalesPerMonth();
}
