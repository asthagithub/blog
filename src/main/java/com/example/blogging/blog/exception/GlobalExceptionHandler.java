package com.example.blogging.blog.exception;

import com.example.blogging.blog.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFound(ResourceNotFoundException ex) {
        ApiResponse apiResponse = new ApiResponse(ex.getMessage(), false);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity validationFailed(MethodArgumentNotValidException ex) {
        Map<String, String> response = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String message = error.getDefaultMessage();
            String fieldName = ((FieldError) error).getField();

            response.put(fieldName, message);
        });
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity validationDeleteApi(HttpRequestMethodNotSupportedException ex){
        ApiResponse apiResponse = new ApiResponse("Provide id which needs to be deleted", false);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
