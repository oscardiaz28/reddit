package com.spring.reddit.controllers;

import com.spring.reddit.controllers.dto.LoginRequest;
import com.spring.reddit.controllers.dto.RegisterRequest;
import com.spring.reddit.service.AuthService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody RegisterRequest registerRequest){
        authService.signup(registerRequest);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token){
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account activate successfully", HttpStatus.OK);
    }

    @PostMapping("/s")
    public String login(@RequestBody LoginRequest loginRequest){

        return "User logged";
    }

}
