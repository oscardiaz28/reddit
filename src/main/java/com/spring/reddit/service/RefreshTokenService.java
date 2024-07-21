package com.spring.reddit.service;

import com.spring.reddit.dto.RefreshToken;
import com.spring.reddit.dto.RefreshTokenRequest;
import com.spring.reddit.exceptions.RedditException;
import com.spring.reddit.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository repository;

    public RefreshToken generateRefreshToken(){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return repository.save(refreshToken);
    }

    void validateRefreshToken(String token){
        repository.findByToken(token).orElseThrow( () -> new RedditException("Invalid refresh token"));
    }

    public void deleteRefreshToken(String token){
        repository.deleteByToken(token);
    }

}
