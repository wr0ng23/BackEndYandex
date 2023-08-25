package dev.wr0ng23.backendyandex.exception;

import dev.wr0ng23.backendyandex.dto.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Error> handleAllException(Exception exception) {
        Error error = new Error(HttpStatus.BAD_REQUEST.value(), "Validation Failed");
        return new ResponseEntity<>(error, HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler
    public ResponseEntity<Error> handleAllException(NotFoundException exception) {
        Error error = new Error(HttpStatus.NOT_FOUND.value(), exception.getMessage());
        return new ResponseEntity<>(error, HttpStatusCode.valueOf(404));
    }
}
