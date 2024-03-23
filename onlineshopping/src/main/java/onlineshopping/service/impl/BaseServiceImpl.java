package onlineshopping.service.impl;

import lombok.RequiredArgsConstructor;
import onlineshopping.constants.UserRole;
import onlineshopping.entity.User;
import onlineshopping.exc.HandleExceptions;
import onlineshopping.model.UserDto;
import onlineshopping.repo.UserRepository;
import onlineshopping.service.base.BaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaseServiceImpl implements BaseService {

    private final UserRepository userRepository;
    @Override
    public ResponseEntity<String> createAccount(UserDto userDto) {
        try {
            User checkExisting = userRepository.findByEmail(userDto.getEmail());
            if (checkExisting != null){
                throw new HandleExceptions("Already user with same email exists");
            }
            User user = getUser(userDto);
            userRepository.save(user);
            return ResponseEntity.ok("You successfully created your account");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to save user: " + e.getMessage());
        }
    }

    private static User getUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setMobile(userDto.getMobile());
        user.setPassword(userDto.getPassword());
        if (userDto.getRole().equalsIgnoreCase("customer") || userDto.getEmail().endsWith("@gmail.com")){
            user.setRole(UserRole.CUSTOMER);
        } else if (userDto.getRole().equalsIgnoreCase("manufacturer") || userDto.getRole().equalsIgnoreCase("saler")) {
            user.setRole(UserRole.ENTREPRENEUR);
        } else if (userDto.getName().equalsIgnoreCase("admin")|| userDto.getEmail().contains("admin")) {
            user.setRole(UserRole.ADMIN);
        }
        return user;
    }
}
