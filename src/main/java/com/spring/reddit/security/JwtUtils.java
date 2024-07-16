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

import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${security.jwt.key.private}")
    private String privateKey;
    @Value("${security.jwt.user.generator}")
    private String userGenerator;

    public String createToken(Authentication authentication) {
        Algorithm algorithm =Algorithm.HMAC256(this.privateKey);
        String username = authentication.getPrincipal().toString();
        String authorities = authentication.getAuthorities()
                .stream()
                .map( grantedAuthority -> grantedAuthority.getAuthority() )
                .collect(Collectors.joining(","));
        String jwtToken = JWT.create()
                .withIssuer(this.userGenerator)
                .withSubject(username)
                .withClaim("authorities", authorities)
                .withJWTId(UUID.randomUUID().toString())
                .withExpiresAt(new Date(System.currentTimeMillis()+1800000))
                .sign(algorithm);
        return jwtToken;
    }

    public DecodedJWT validateToken(String jwt){
        try{
            Algorithm algorithm =Algorithm.HMAC256(this.privateKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.userGenerator)
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
