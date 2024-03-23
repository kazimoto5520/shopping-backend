package onlineshopping.service.base;

import onlineshopping.model.UserDto;
import org.springframework.http.ResponseEntity;

public interface BaseService {
    ResponseEntity<String> createAccount(UserDto userDto);
}
//todo: saving orders
//todo: jwt configuration
//todo: sign in users
//todo: payment methods
//todo: notifications including otp via sms
//todo: search button enable (pageable format)
//todo: admin page filling eg sales report
//todo: mail configurations
