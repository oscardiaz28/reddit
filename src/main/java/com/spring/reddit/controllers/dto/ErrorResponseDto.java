package com.spring.reddit.controllers.dto;

import org.springframework.http.HttpStatus;

public record ErrorResponseDto(
        String message,
        String error,
        HttpStatus statusCode
) {
}
