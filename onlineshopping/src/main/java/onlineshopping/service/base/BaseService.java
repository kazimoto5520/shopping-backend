package onlineshopping.service.base;

import onlineshopping.model.AuthRequest;
import onlineshopping.model.AuthResponse;
import onlineshopping.model.UserDto;
import org.springframework.http.ResponseEntity;

public interface BaseService {
    ResponseEntity<AuthResponse> createAccount(UserDto userDto);

    AuthResponse authenticate(AuthRequest request);
}
//todo: payment methods
//todo: notifications including otp via sms
//todo: search button enable (pageable format)
//todo: admin page filling eg sales report
//todo: mail configurations
