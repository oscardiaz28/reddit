package com.spring.reddit.service;

import com.spring.reddit.dto.AuthResponse;
import com.spring.reddit.dto.LoginRequest;
import com.spring.reddit.dto.RegisterRequest;
import com.spring.reddit.exceptions.RedditException;
import com.spring.reddit.model.NotificationEmail;
import com.spring.reddit.model.User;
import com.spring.reddit.model.VerificationToken;
import com.spring.reddit.repository.UserRepository;
import com.spring.reddit.repository.VerificationTokenRepository;
import com.spring.reddit.security.JwtUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtils jwtUtils;

    public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository,
                       VerificationTokenRepository verificationTokenRepository, MailService mailService,
                       UserDetailsServiceImpl userDetailsService, JwtUtils jwtUtils){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.mailService = mailService;
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }

    @Transactional
    public void signup(RegisterRequest registerRequest){
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword( passwordEncoder.encode(registerRequest.getPassword()) );
        user.setUsername(registerRequest.getUsername());
        user.setCreated(Instant.now());
        user.setEnabled(false);
        userRepository.save(user);
        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail(
                "Please Activate your Account",
                user.getEmail(),
                "http://localhost:8080/api/auth/accountVerification/"+token
        ));
    }

    public AuthResponse login(LoginRequest loginRequest){
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        Authentication authentication = this.authentication(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.createToken(authentication);
        return new AuthResponse(authentication.getPrincipal().toString(), token, "user login");
    }

    private Authentication authentication(String email, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        if( userDetails == null ){
            throw new RedditException("Invalid email");
        }
        if( !passwordEncoder.matches(password, userDetails.getPassword()) ){
            throw new RedditException("Password not valid");
        }
        return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities());
    }

    private String generateVerificationToken(User user){
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow( () -> new RedditException("Invalid token"));
        fetchUserAndEnabled(verificationToken.get());
    }

    @Transactional
    private void fetchUserAndEnabled(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        Optional<User> user = userRepository.findByUsername(username);
        user.orElseThrow( () -> new RedditException("User not found " + username) );
        user.get().setEnabled(true);
        userRepository.save(user.get());
    }


}
