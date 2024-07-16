package com.spring.reddit.dto;

import org.springframework.http.HttpStatus;

public record ErrorResponseDto(
        String message,
        String error,
        HttpStatus statusCode
) {
}
