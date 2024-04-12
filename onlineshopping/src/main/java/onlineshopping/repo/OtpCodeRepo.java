package onlineshopping.repo;

import onlineshopping.entity.Otp;
import onlineshopping.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpCodeRepo extends JpaRepository<Otp, Long> {

    Otp findByCustomer(Customer customerWithOtp);
}
