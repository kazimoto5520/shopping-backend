package onlineshopping.notification.service;

import lombok.RequiredArgsConstructor;
import onlineshopping.exc.HandleExceptions;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


@Service
@RequiredArgsConstructor
public class BeemOtpService {

    private static final String API_KEY = "4aad866c79479cf3";
    private static final String BEEM_URL = "https://apisms.beem.africa/v1/send";  // Update this to the correct endpoint
    private final Logger log = Logger.getLogger(BeemOtpService.class.getName());

    public void sendOtp(String phoneNumber, String otpCode) {
        try {
            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_2)
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            String message = "Your OTP code is: " + otpCode;
            String requestBody = String.format("{\"api_key\":\"%s\",\"recipient\":\"%s\",\"message\":\"%s\"}",
                    API_KEY, phoneNumber, message);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BEEM_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "ApiKey " + API_KEY)  // Use correct authorization header if required
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                log.info("OTP sent successfully to " + phoneNumber);
            } else {
                log.log(Level.SEVERE, "Failed to send OTP. HTTP error code: {0}", response.statusCode());
                log.log(Level.SEVERE, "Response: {0}", response.body());
            }

        } catch (Exception e) {
            log.log(Level.SEVERE, "Exception occurred while sending OTP", e);
            throw new HandleExceptions("Exception occurred while sending OTP: " + e.getMessage(), e);
        }
    }
    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}
