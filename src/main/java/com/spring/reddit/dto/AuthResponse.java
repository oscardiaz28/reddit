package com.spring.reddit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public record AuthResponse(
        String username,
        String jwt,
        String message
) {
}
