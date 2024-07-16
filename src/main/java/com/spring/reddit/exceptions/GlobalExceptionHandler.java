package com.spring.reddit.exceptions;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.spring.reddit.controllers.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handle(HttpRequestMethodNotSupportedException ex){
        String message = ex.getMessage();
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(message, "Not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RedditException.class)
    public ResponseEntity<?> handleRedditException(RedditException ex){
        String message = "Reddit exception";
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(message, ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<?> handleJwtVerification(JWTVerificationException ex){
        String message = "Invalid token";
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(message, ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }


}
