package onlineshopping.exc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DatabaseExceptionHandler {

    @ExceptionHandler(value = {DatabaseAccessException.class})
    public ResponseEntity<Object> handleDatabaseException(DatabaseAccessException accessException){
        GlobalExceptionFields fields = new GlobalExceptionFields(
                accessException.getMessage(),
                accessException.getCause(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        return new ResponseEntity<>(fields,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
