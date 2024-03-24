package onlineshopping.service.impl;

import lombok.RequiredArgsConstructor;
import onlineshopping.constants.UserRole;
import onlineshopping.entity.User;
import onlineshopping.exc.HandleExceptions;
import onlineshopping.jwt.service.JwtService;
import onlineshopping.model.AuthRequest;
import onlineshopping.model.AuthResponse;
import onlineshopping.model.UserDto;
import onlineshopping.repo.UserRepo;
import onlineshopping.service.base.BaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaseServiceImpl implements BaseService {

    private final UserRepo userRepo;
    private final JwtService jwtService;
    private  final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<AuthResponse> createAccount(UserDto userDto) {
        try {
            User checkExisting = userRepo.findByEmail(userDto.getEmail());
            if (checkExisting != null){
                throw new HandleExceptions("Already user with same email exists");
            }

            User user = new User();
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setMobile(userDto.getMobile());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            if (userDto.getRole().equalsIgnoreCase("customer")/* || userDto.getEmail().endsWith("@gmail.com")*/){
                user.setRole(UserRole.CUSTOMER);
            } else if (userDto.getRole().equalsIgnoreCase("manufacturer") || userDto.getRole().equalsIgnoreCase("saler")) {
                user.setRole(UserRole.ENTREPRENEUR);
            } else if (userDto.getName().equalsIgnoreCase("admin")|| userDto.getEmail().contains("admin")) {
                user.setRole(UserRole.ADMIN);
            }

            userRepo.save(user);
            var jwt = jwtService.generateToken(user);
            AuthResponse response = new AuthResponse(jwt);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            AuthResponse error = new AuthResponse("Error occurred!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(error);
        }
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepo.findByEmail(request.getEmail());
        var token = jwtService.generateToken(user);
        return AuthResponse.builder().token(token).build();
    }
}
