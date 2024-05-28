package onlineshopping.notification.service;

import lombok.RequiredArgsConstructor;
import onlineshopping.exc.HandleExceptions;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Base64;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


@Service
@RequiredArgsConstructor
public class BeemOtpService {
    private static final String API_KEY = "323037f69fbc3e2b";
    private static final String SECRET_KEY = "NTZjOWY2Mjc4Y2Y5ODk3ZmUwMzZlN2Y3NWNiMjA0NmIxMDRiMTU1YTAwZDcxZmYwZjdiNzFlNWQzZmZlNzMyYQ==";
    private static final String BEEM_URL = "https://apisms.beem.africa/v1/send";  // Update this to the correct endpoint
    private final Logger log = Logger.getLogger(BeemOtpService.class.getName());

    public void sendOtp(String phoneNumber, String otpCode) {
        try {
            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_2)
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            String message = "Your OTP code is: " + otpCode;

            // Create the request body
            String requestBody = String.format(
                    "{\"source_addr\":\"INFO\"," +
                            "\"schedule_time\":\"\"," +
                            "\"encoding\":\"0\"," +
                            "\"message\":\"%s\"," +
                            "\"recipients\":[{\"recipient_id\":1,\"dest_addr\":\"%s\"}]}",
                    message, phoneNumber
            );

            // Encode API key and secret key for authorization
            String auth = Base64.getEncoder().encodeToString((API_KEY + ":" + SECRET_KEY).getBytes());

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BEEM_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Basic " + auth)  // Using Basic Auth with encoded API key and secret key
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
