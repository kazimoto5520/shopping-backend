package onlineshopping.exc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GlobalExceptionFields {
    private String message;
    private Throwable throwable;
    private HttpStatus http;
}
