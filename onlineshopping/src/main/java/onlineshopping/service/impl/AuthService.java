package onlineshopping.service.impl;

import lombok.RequiredArgsConstructor;
import onlineshopping.constants.UserRole;
import onlineshopping.entity.Otp;
import onlineshopping.entity.User;
import onlineshopping.exc.HandleExceptions;
import onlineshopping.exc.InvalidOtpException;
import onlineshopping.jwt.service.JwtService;
import onlineshopping.model.AuthRequest;
import onlineshopping.model.AuthResponse;
import onlineshopping.model.UserDto;
import onlineshopping.notification.model.LoginRequest;
import onlineshopping.notification.service.OtpService;
import onlineshopping.repo.OtpCodeRepo;
import onlineshopping.repo.UserRepo;
import onlineshopping.service.base.BaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService implements BaseService {

    private final UserRepo userRepo;
    private final JwtService jwtService;
    private  final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final OtpService otpService;
    private final OtpCodeRepo otpCodeRepo;

    private static final int OTP_EXPIRATION_MINUTES = 15;

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

            // otp
            String otpCode = otpService.generateOtp();
            otpService.sendOtp(userDto.getMobile(), otpCode);

            //storing otp
            Otp otp = Otp.builder()
                    .otpCode(otpCode)
                    .user(user)
                    .build();
            otpCodeRepo.save(otp);

            AuthResponse response = new AuthResponse("OTP sent successfully to your phone number");
            return ResponseEntity.ok(response);
        }
        catch (HandleExceptions exceptions){
            AuthResponse authResponse = new AuthResponse(exceptions.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(authResponse);
        }
        catch (Exception e){
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

    @Override
    public ResponseEntity<AuthResponse> verifyOtp(LoginRequest loginRequest) {
        try {
            User userWithOtp = userRepo.findByMobile(loginRequest.getMobile());

            if (userWithOtp == null){
                throw new HandleExceptions("Oops! user not found");
            }
            else {
                Otp otpWithUser = otpCodeRepo.findByUser(userWithOtp);
                 String storedOtp = otpWithUser.getOtpCode();

                 if (!loginRequest.getOtp().equals(storedOtp)){
                     throw new HandleExceptions("Invalid or Expired OTP");
                 }
                  else if (LocalDateTime.now().minusMinutes(OTP_EXPIRATION_MINUTES).isAfter(otpWithUser.getCreatedAt())) {
                    throw new InvalidOtpException("OTP has expired");
                 }
                  else {
                    String jwt = jwtService.generateToken(userWithOtp);

                     AuthResponse authResponse = AuthResponse
                             .builder()
                             .token(jwt)
                             .build();

                     return ResponseEntity.ok(authResponse);
                  }
            }
        }
        catch (HandleExceptions exceptions) {
            AuthResponse authResponse = new AuthResponse(exceptions.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(authResponse);
        }
        catch (Exception e) {
            AuthResponse error = new AuthResponse("Error occurred!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }

    }

}
