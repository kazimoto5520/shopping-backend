package onlineshopping.repo;

import onlineshopping.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Customer,Long> {

    Customer findByEmail(String email);

    Customer findByMobile(String mobile);

}
