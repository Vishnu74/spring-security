package com.example.appsecurity.exception;

import com.example.appsecurity.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.access.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationErrors(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach((error) ->
                errors.put(error.getField(), error.getDefaultMessage()));
        ApiResponse<Map<String, String>> response = ApiResponse.<Map<String, String>>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Validation Failed")
                .timestamp(System.currentTimeMillis())
                .errors(errors)
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<String>> handleBusinessException(BusinessException ex) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(ex.getStatus().value())
                .message(ex.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(response, ex.getStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<String>> handleAccessDenied(AccessDeniedException ex) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(HttpStatus.FORBIDDEN.value())
                .message("You do not have permission to access this resource")
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGeneralException(Exception ex) {
        ex.printStackTrace(); // 👈 keep during dev to see root cause in console
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}