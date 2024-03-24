package onlineshopping.contoller;

import lombok.RequiredArgsConstructor;
import onlineshopping.model.UserDto;
import onlineshopping.service.impl.BaseServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/user")
@RequiredArgsConstructor
public class BaseController {

    private final BaseServiceImpl userService;


    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(
            @RequestBody UserDto userDto
    ){
        return userService.createAccount(userDto);
    }
}