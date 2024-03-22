package onlineshopping.exc;

public class HandleExceptions extends RuntimeException{
    public HandleExceptions(String message) {
        super(message);
    }

    public HandleExceptions(String message, Throwable cause) {
        super(message, cause);
    }
}
