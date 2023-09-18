package ru.vksponsorblock.VKSponsorBlock.security.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.vksponsorblock.VKSponsorBlock.models.User;

import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.issuer}")
    private String jwtIssuer;
    @Value("${jwt.subject}")
    private String jwtSubject;
    @Value("${jwt.user-id-claim}")
    private String userIdClaim;
    @Value("${jwt.username-claim}")
    private String usernameClaim;

    public String generateToken(User user) {
        return JWT.create()
                .withSubject(jwtSubject)
                .withClaim(userIdClaim, user.getId().toString())
                .withClaim(usernameClaim, user.getUsername())
                .withIssuedAt(new Date())
                .withIssuer(jwtIssuer)
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    public DecodedJWT validateToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtSecret))
                .withSubject(jwtSubject)
                .withIssuer(jwtIssuer)
                .build();
        return verifier.verify(token);
    }
}
