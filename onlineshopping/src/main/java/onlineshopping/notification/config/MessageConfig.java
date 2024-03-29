package onlineshopping.notification.config;

import ClickSend.ApiClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Data
public class MessageConfig {

    @Value("${clickSend-username}")
    private String clickSendUsername;
    @Value("${clickSend-apiKey}")
    private String clickSendApiKey;
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ApiClient clickSendConfig() {
        ApiClient clickSendApiClient = new ApiClient();
        clickSendApiClient.setUsername(clickSendUsername);
        clickSendApiClient.setPassword(clickSendApiKey);
        return clickSendApiClient;
    }
}
