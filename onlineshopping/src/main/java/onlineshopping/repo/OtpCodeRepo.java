package onlineshopping.repo;

import onlineshopping.entity.Otp;
import onlineshopping.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpCodeRepo extends JpaRepository<Otp, Long> {

    Otp findByUser(User userWithOtp);
}
