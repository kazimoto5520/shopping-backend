package onlineshopping.contoller;

import lombok.RequiredArgsConstructor;
import onlineshopping.model.AuthRequest;
import onlineshopping.model.AuthResponse;
import onlineshopping.model.UserDto;
import onlineshopping.service.impl.BaseServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/base")
@RequiredArgsConstructor
public class BaseController {

    private final BaseServiceImpl baseService;


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> registerUser(
            @RequestBody UserDto userDto
    ){
        return baseService.createAccount(userDto);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody AuthRequest request)
    {
        return ResponseEntity.ok(baseService.authenticate(request));
    }
}
