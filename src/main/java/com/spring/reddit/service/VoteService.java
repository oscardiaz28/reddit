package com.spring.reddit.service;

import com.spring.reddit.dto.VoteDto;
import com.spring.reddit.exceptions.RedditException;
import com.spring.reddit.model.Post;
import com.spring.reddit.model.Vote;
import com.spring.reddit.model.VoteType;
import com.spring.reddit.repository.PostRepository;
import com.spring.reddit.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteService {

    private final PostRepository postRepository;
    private final VoteRepository voteRepository;
    private final AuthService authService;

    public void vote(VoteDto voteDto){
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow( () -> new RedditException("Post not found"));
        Optional<Vote>  voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByIdDesc(post, authService.getCurrentUser());
        if ( voteByPostAndUser.isPresent() &&
         voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())){
            throw new RedditException("You have already vote for this post");
        }
        if(VoteType.UPVOTE.equals(voteDto.getVoteType()) ){
            post.setVoteCount(post.getVoteCount()+1);
        }else{
            post.setVoteCount(post.getVoteCount()-1);
        }
        voteRepository.save( mapToVote(voteDto, post) );
        postRepository.save(post);
    }
    private Vote mapToVote(VoteDto voteDto, Post post){
        Vote vote = new Vote();
        vote.setVoteType(voteDto.getVoteType());
        vote.setPost(post);
        vote.setUser(authService.getCurrentUser());
        return vote;
    }

}
