package com.spring.reddit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CommentResponse {
    private Long id;
    private String postName;
    private String username;
}
