package onlineshopping.contoller;

import lombok.RequiredArgsConstructor;
import onlineshopping.model.AuthRequest;
import onlineshopping.model.AuthResponse;
import onlineshopping.model.UserDto;
import onlineshopping.notification.model.LoginRequest;
import onlineshopping.service.impl.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/base")
@RequiredArgsConstructor
public class BaseController {

    private final AuthService baseService;


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> registerUser(
            @RequestBody UserDto userDto
    ){
        return baseService.createAccount(userDto);
    }

    @PostMapping("/verifyOtpCode")
    public ResponseEntity<AuthResponse> verifyOtpCodeAndLogin(
            @RequestBody LoginRequest loginRequest
    ){
        return baseService.verifyOtp(loginRequest);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody AuthRequest request)
    {
        return baseService.authenticate(request);
    }

    @PostMapping("/resendOtpCodes")
    public ResponseEntity<AuthResponse> resendOtpCodes(
            @RequestParam String phoneNumber,
            @RequestParam String oldOtpCodes
    ){
        return baseService.resendOtpCodes(phoneNumber, oldOtpCodes);
    }
}
