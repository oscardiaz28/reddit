package com.spring.reddit.service;

import com.spring.reddit.dto.CommentsDto;
import com.spring.reddit.exceptions.RedditException;
import com.spring.reddit.mapper.CommentMapper;
import com.spring.reddit.model.Comment;
import com.spring.reddit.model.NotificationEmail;
import com.spring.reddit.model.Post;
import com.spring.reddit.model.User;
import com.spring.reddit.repository.CommentRepository;
import com.spring.reddit.repository.PostRepository;
import com.spring.reddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentsService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentRepository commentRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public void save(CommentsDto dto){
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow( () -> new RedditException("Post not found"));
        User user = authService.getCurrentUser();
        Comment comment = CommentMapper.dtoToComment(dto, post, user);
        Comment commentSaved = commentRepository.save(comment);

        String msg = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post");
        sendCommentNotification(msg, post.getUser());
    }

    public List<CommentsDto> getCommentsByPost(Long id){
        Post post = postRepository.findById(id).orElseThrow( () -> new RedditException("Post not found") );
        List<Comment> comments = commentRepository.findCommentsByPostId(post.getPostId());
        return comments.stream()
                .map( comment -> CommentMapper.commentToDto(comment))
                .collect(Collectors.toList());
    }

    private void sendCommentNotification(String msg, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " commented on your post", user.getEmail(), msg ));
    }


}
