package onlineshopping.exc;

public class InvalidOtpException extends RuntimeException{
    public InvalidOtpException(String message) {
        super(message);
    }
}
