package onlineshopping.exc;

public class SearchExceptions extends RuntimeException{
    public SearchExceptions(String message) {
        super(message);
    }

    public SearchExceptions(String message, Throwable cause) {
        super(message, cause);
    }
}
