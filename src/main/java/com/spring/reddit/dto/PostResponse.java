package com.spring.reddit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {
    private Long id;
    private String name;
    private String description;
    private String username;
    private String subreddit;
    private Integer voteCount;
    private Integer commentCount;
    private String duration;
}
