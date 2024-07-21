package com.spring.reddit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

public record AuthResponse(
        String username,
        String authenticationToken,
        String refreshToken,
        Instant expiresAt,
        String message
) {
}
