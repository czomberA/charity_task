package org.example.sii_task;

import org.example.sii_task.errorHandling.*;
import org.example.sii_task.models.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex) {
        return new ResponseEntity<>(new ApiError(ex.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return new ResponseEntity<>(new ApiError(message, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DoesNotExist.class)
    public ResponseEntity<ApiError> handleFundraiserNotFound(DoesNotExist ex) {
        ApiError error = new ApiError(
                ex.getMessage(),
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAll(Exception ex) {
        ApiError error = new ApiError("Unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ApiError> handleFundraiserExists(AlreadyExistsException ex) {
        ApiError error = new ApiError(ex.getMessage(), HttpStatus.CONFLICT);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadCurrency.class)
    public ResponseEntity<ApiError> handleBadCurrency(BadCurrency ex) {
        ApiError error = new ApiError(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotAssigned.class)
    public ResponseEntity<ApiError> handleNotAssigned(NotAssigned ex) {
        ApiError error = new ApiError(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyAssigned.class)
    public ResponseEntity<ApiError> handleAlreadyAssigned(AlreadyAssigned ex) {
        ApiError error = new ApiError(ex.getMessage(), HttpStatus.CONFLICT);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotEmpty.class)
    public ResponseEntity<ApiError> handleEmpty(NotEmpty ex) {
        ApiError error = new ApiError(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NbpException.class)
    public ResponseEntity<ApiError> handleNbp(NbpException ex) {
        ApiError error = new ApiError(ex.getMessage(), HttpStatus.BAD_GATEWAY);
        return new ResponseEntity<>(error, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(NegativeDonationException.class)
    public ResponseEntity<ApiError> handleNegativeDonation(NegativeDonationException ex) {
        ApiError error = new ApiError(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


}
