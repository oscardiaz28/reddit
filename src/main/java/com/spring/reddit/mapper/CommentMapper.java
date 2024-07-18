package com.spring.reddit.mapper;

import com.spring.reddit.dto.CommentsDto;
import com.spring.reddit.model.Comment;
import com.spring.reddit.model.Post;
import com.spring.reddit.model.User;

import java.time.Instant;

public class CommentMapper {

    public static Comment dtoToComment(CommentsDto dto, Post post, User user){
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setText(dto.getText());
        comment.setCreatedDate(Instant.now());
        return comment;
    }

    public static CommentsDto commentToDto(Comment comment){
        return CommentsDto.builder()
                .id(comment.getId())
                .postId(comment.getPost().getPostId())
                .createdDate(comment.getCreatedDate())
                .text(comment.getText())
                .username(comment.getUser().getUsername())
                .build();
    }
}
