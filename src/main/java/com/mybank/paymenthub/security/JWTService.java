package com.mybank.paymenthub.security;

import com.mybank.paymenthub.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JWTService {

    private static final String SECRET_KEY =
            "mysecretkeymysecretkeymysecretkey12345";

    private final SecretKey key =
            Keys.hmacShaKeyFor(
                    SECRET_KEY.getBytes(StandardCharsets.UTF_8)
            );


    // =========================================================
    // Generate Token
    // =========================================================

    public String generateToken(
            String username,
            Role role
    ) {

        return Jwts.builder()

                .subject(username)

                .claim(
                        "role",
                        role.name()
                )

                .issuedAt(
                        new Date(
                                System.currentTimeMillis()
                        )
                )

                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000 * 60 * 60
                        )
                )

                .signWith(key)

                .compact();
    }


    // =========================================================
    // Extract Username
    // =========================================================

    public String extractUsername(
            String token
    ) {

        return extractAllClaims(token)
                .getSubject();
    }


    // =========================================================
    // Extract All Claims
    // =========================================================

    private Claims extractAllClaims(
            String token
    ) {

        return Jwts.parser()

                .verifyWith(key)

                .build()

                .parseSignedClaims(token)

                .getPayload();
    }


    // =========================================================
    // Validate Token
    // =========================================================

    public boolean isTokenValid(
            String token,
            UserDetails userDetails
    ) {

        final String username =
                extractUsername(token);

        return username.equals(
                userDetails.getUsername()
        )
                && !isTokenExpired(token);
    }


    // =========================================================
    // Check Token Expired
    // =========================================================

    private boolean isTokenExpired(
            String token
    ) {

        return extractExpiration(token)
                .before(new Date());
    }


    // =========================================================
    // Extract Expiration
    // =========================================================

    private Date extractExpiration(
            String token
    ) {

        return extractAllClaims(token)
                .getExpiration();
    }
}