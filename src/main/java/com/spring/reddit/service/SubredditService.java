package com.spring.reddit.service;

import com.spring.reddit.dto.SubredditDto;
import com.spring.reddit.mapper.SubredditMapper;
import com.spring.reddit.model.Subreddit;
import com.spring.reddit.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto){
        Subreddit save = subredditRepository.save(  SubredditMapper.dtoToSubreddit(subredditDto) );
        subredditDto.setId( save.getId() );
        return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll(){
        return subredditRepository.findAll().stream()
                .map( subreddit -> SubredditMapper.subredditToDto(subreddit))
                .collect(Collectors.toList());
    }


}
