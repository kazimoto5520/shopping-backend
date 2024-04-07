package onlineshopping.notification.service;

import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpService {

    private TwilioRestClient twilioRestClient;

    @Value("${twilio.phoneNumber}")
    private String twilioPhoneNumber;

    public OtpService() {
    }

    public OtpService(TwilioRestClient twilioRestClient) {
        this.twilioRestClient = twilioRestClient;
    }

    public OtpService(TwilioRestClient twilioRestClient, String twilioPhoneNumber) {
        this.twilioRestClient = twilioRestClient;
        this.twilioPhoneNumber = twilioPhoneNumber;
    }

    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public void sendOtp(String recipientPhoneNumber, String otpCode) {
        Message.creator(new PhoneNumber(recipientPhoneNumber), new PhoneNumber(twilioPhoneNumber),
                        "Your OTP code is: " + otpCode)
                .create(twilioRestClient);
    }

}
