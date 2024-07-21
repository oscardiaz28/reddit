package com.spring.reddit.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${security.jwt.key.private}")
    private String privateKey;
    @Value("${security.jwt.user.generator}")
    private String userGenerator;
    @Value("${jwt.expiration.time}")
    private Long jwtExpirationTime;

    public Map<String, Object > createToken(Authentication authentication, Long expirationTime) {
        Map<String, Object> data = new HashMap<>();

        Algorithm algorithm =Algorithm.HMAC256(this.privateKey);
        String username = authentication.getPrincipal().toString();
        String authorities = authentication.getAuthorities()
                .stream()
                .map( grantedAuthority -> grantedAuthority.getAuthority() )
                .collect(Collectors.joining(","));
        Instant time = Instant.now().plusMillis(expirationTime);
        String jwtToken = JWT.create()
                .withIssuer(this.userGenerator)
                .withSubject(username)
                .withClaim("authorities", authorities)
                .withJWTId(UUID.randomUUID().toString())
                .withExpiresAt( time )
                .sign(algorithm);
        data.put("token", jwtToken);
        data.put("time", time );
        return data;
    }

    public String generateTokenWithUsername(String username){
        Algorithm algorithm =Algorithm.HMAC256(this.privateKey);
        Instant time = Instant.now().plusMillis(jwtExpirationTime);
        String jwt = JWT.create()
                .withIssuer(this.userGenerator)
                .withClaim("authorities", "USER")
                .withExpiresAt(time)
                .withSubject(username)
                .sign(algorithm);
        return jwt;
    }

    public Long getJwtExpirationTime(){
        return jwtExpirationTime;
    }

    public DecodedJWT validateToken(String jwt){
        try{
            Algorithm algorithm =Algorithm.HMAC256(this.privateKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.userGenerator) // Validar el emisor
                    .build();
            return verifier.verify(jwt);
        }catch(JWTVerificationException e){
            throw new JWTVerificationException("Token invalido");
        }
    }

    public String getUsername(DecodedJWT decodedJWT){
        return decodedJWT.getSubject().toString();
    }

    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claim){
        return decodedJWT.getClaim(claim);
    }


}
