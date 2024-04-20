package onlineshopping.exc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(value = {HandleExceptions.class})
    public ResponseEntity<Object> handleExceptions(HandleExceptions handleExceptions){
        GlobalExceptionFields exceptionFields = new GlobalExceptionFields(
                handleExceptions.getMessage(),
                handleExceptions.getCause(),
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(exceptionFields,HttpStatus.BAD_REQUEST);
    }


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
