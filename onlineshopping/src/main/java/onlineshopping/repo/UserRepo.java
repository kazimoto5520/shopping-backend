package onlineshopping.repo;

import onlineshopping.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Customer,Long> {

    Customer findByEmail(String email);

    Customer findByMobile(String mobile);

    @Query("SELECT u.name, u.email, u.mobile, u.enrollNumber, u.date_created " +
            "FROM Customer u " +
            "ORDER BY u.date_created "
    )
    Page<Object[]> findAllUsers(Pageable pageable);

}
