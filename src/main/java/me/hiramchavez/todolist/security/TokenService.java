package me.hiramchavez.todolist.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import me.hiramchavez.todolist.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.jwt_secret}")
    private String apiSecret;

    @Value("${api.security.issuer}")
    private String apiIssuer;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);

            return JWT.create()
              .withIssuer(apiIssuer)
              .withSubject(user.getEmail())
              .withClaim("id", user.getId())
              .withExpiresAt(getExpirationTime())
              .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error generating token. " + e.getMessage());
        }
    }

    private Instant getExpirationTime() {
        return LocalDateTime.now().plusDays(14).toInstant(ZoneOffset.of("-06:00"));
    }

}
