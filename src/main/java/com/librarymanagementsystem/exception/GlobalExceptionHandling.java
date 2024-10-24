package com.librarymanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandling {

    @ExceptionHandler(BookException.class)
    public ResponseEntity<ApiException> handleBookNotFoundException(BookException ex) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(
                ex.getMessage(),
                status
        );
        return new ResponseEntity<>(apiException, status);
    }

    @ExceptionHandler(PatronException.class)
    public ResponseEntity<ApiException> handlePatronNotFoundException(PatronException ex) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(
                ex.getMessage(),
                status
        );
        return new ResponseEntity<>(apiException, status);
    }

    @ExceptionHandler(BorrowingRecordException.class)
    public ResponseEntity<ApiException> handleborrowRecordNotFoundException(BorrowingRecordException ex) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(
                ex.getMessage(),
                status
        );
        return new ResponseEntity<>(apiException, status);
    }

}
