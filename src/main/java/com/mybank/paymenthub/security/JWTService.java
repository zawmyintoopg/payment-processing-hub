package com.mybank.paymenthub.security;

import com.mybank.paymenthub.enums.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;


@Service
public class JWTService {


    private final String SECRET_KEY =
            "mysecretkeymysecretkeymysecretkey12345";
    private final SecretKey key =
            Keys.hmacShaKeyFor(
                    SECRET_KEY.getBytes()
            );

    public String generateToken(
            String username,
            Role role
    ){

        return Jwts.builder()

                .setSubject(username)

                .claim(
                        "role",
                        role.name()
                )

                .setIssuedAt(
                        new Date(
                                System.currentTimeMillis()
                        )
                )

                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000 * 60 * 60
                        )
                )

                .signWith(
                       key
                )

                .compact();
    }



    public String extractUsername(String token){

        return Jwts.parser()

                .setSigningKey(key)

                .parseClaimsJws(token)

                .getBody()

                .getSubject();
    }



    public boolean isTokenValid(
            String token,
            UserDetails userDetails
    ){

        final String username =
                extractUsername(token);


        return username.equals(
                userDetails.getUsername()
        )
                && !isTokenExpired(token);

    }



    private boolean isTokenExpired(String token){

        return extractExpiration(token)
                .before(new Date());

    }



    private Date extractExpiration(String token){

        return Jwts.parser()

                .setSigningKey(key)

                .parseClaimsJws(token)

                .getBody()

                .getExpiration();
    }

}