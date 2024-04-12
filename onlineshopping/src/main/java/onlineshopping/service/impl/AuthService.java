package onlineshopping.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlineshopping.constants.UserRole;
import onlineshopping.entity.Customer;
import onlineshopping.entity.Otp;
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
@Slf4j
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
            Customer checkExisting = userRepo.findByEmail(userDto.getEmail());
            if (checkExisting != null){
                throw new HandleExceptions("Already user with same email exists");
            }

            Customer customer = new Customer();
            customer.setName(userDto.getName());
            customer.setEmail(userDto.getEmail());
            customer.setMobile(userDto.getMobile());
            customer.setPassword(passwordEncoder.encode(userDto.getPassword()));
            if (userDto.getRole().equalsIgnoreCase("customer")/* || userDto.getEmail().endsWith("@gmail.com")*/){
                customer.setRole(UserRole.CUSTOMER);
            } else if (userDto.getRole().equalsIgnoreCase("manufacturer") || userDto.getRole().equalsIgnoreCase("saler")) {
                customer.setRole(UserRole.ENTREPRENEUR);
            } else if (userDto.getName().equalsIgnoreCase("admin")|| userDto.getEmail().contains("admin")) {
                customer.setRole(UserRole.ADMIN);
            }

            userRepo.save(customer);

            // otp
            String otpCode = otpService.generateOtp();
            log.info("Generated OTP code for mobile {}: {}", userDto.getMobile(), otpCode);
            /*otpService.sendOtp(userDto.getMobile(), otpCode);*/

            //storing otp
            Otp otp = Otp.builder()
                    .otpCode(otpCode)
                    .createdAt(LocalDateTime.now())
                    .customer(customer)
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
            Customer customerWithOtp = userRepo.findByMobile(loginRequest.getMobile());

            if (customerWithOtp == null){
                throw new HandleExceptions("Oops! user not found");
            }
            else {
                Otp otpWithUser = otpCodeRepo.findByCustomer(customerWithOtp);
                 String storedOtp = otpWithUser.getOtpCode();

                 if (!loginRequest.getOtp().equals(storedOtp)){
                     throw new HandleExceptions("Invalid or Expired OTP");
                 }
                  else if (LocalDateTime.now().minusMinutes(OTP_EXPIRATION_MINUTES).isAfter(otpWithUser.getCreatedAt())) {
                    throw new InvalidOtpException("OTP has expired");
                 }
                  else {
                      // generate token for user with valid otp codes
                    String jwt = jwtService.generateToken(customerWithOtp);

                    // clear the otp entity associated with customer from database
                     otpCodeRepo.delete(otpWithUser);

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
