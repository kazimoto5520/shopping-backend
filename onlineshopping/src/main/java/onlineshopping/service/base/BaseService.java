package onlineshopping.service.base;

import onlineshopping.model.AuthRequest;
import onlineshopping.model.AuthResponse;
import onlineshopping.model.UserDto;
import onlineshopping.notification.model.LoginRequest;
import org.springframework.http.ResponseEntity;

public interface BaseService {
    ResponseEntity<AuthResponse> createAccount(UserDto userDto);

    ResponseEntity<AuthResponse> authenticate(AuthRequest request);

    ResponseEntity<AuthResponse> verifyOtp(LoginRequest loginRequest);

    ResponseEntity<AuthResponse> resendOtpCodes(String phoneNumber, String oldOtpCodes);

}

