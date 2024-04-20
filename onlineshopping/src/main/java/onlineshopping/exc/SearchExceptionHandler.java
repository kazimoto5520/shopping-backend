package onlineshopping.exc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SearchExceptionHandler {

    @ExceptionHandler(value = {SearchExceptions.class})
    public ResponseEntity<Object> handleNoSuchFieldException(SearchExceptions searchExceptions){
        GlobalExceptionFields fields = new GlobalExceptionFields(
                searchExceptions.getMessage(),
                searchExceptions.getCause(),
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(fields, HttpStatus.NOT_FOUND);
    }
}
