package com.spring.reddit.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public record AuthResponse(
        String username,
        String jwt,
        String message
) {
}
