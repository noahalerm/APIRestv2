package com.example.apirestv2.security.jwt;

import com.example.apirestv2.model.entities.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.util.Date;

@Log
@Component
public class JwtProvider {
    //ATTRIBUTES
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";

    @Value("${jwt.secret:EnUnLugarDeLaManchaDeCuyoNombreNoQuieroAcordarmeNoHaMuchoTiempoQueViviaUnHidalgo}")
    private String jwtSecret;

    @Value("${jwt.token-expiration:86400}")
    private int jwtTokenDurationInSeconds;

    //METHODS
    /**
     * This method is used to generate a new token.
     * @param authentication Authentication
     * @return Token (String)
     */
    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date tokenExpirationDate = new Date(System.currentTimeMillis() + (jwtTokenDurationInSeconds * 1000L));

        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", TOKEN_TYPE)
                .setSubject(Long.toString(user.getId()))
                .setIssuedAt(new Date())
                .setExpiration(tokenExpirationDate)
                .claim("fullname", user.getUsername())
                .claim("roles", user.getRole())
                .compact();
    }

    /**
     * This method is used to get a user's ID from their token.
     * @param token Token
     * @return ID (Long)
     */
    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    /**
     * This method is used to validate a given token.
     * @param authToken Token to be validated
     * @return true (Token is valid) or false (Token is not valid)
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret.getBytes()).parseClaimsJws(authToken);

            return true;
        } catch (SignatureException ex) {
            log.info("Error in the JWT token signature: " + ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.info("Malformed token: " + ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.info("The token has expired: " + ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.info("JWT token not supported: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.info("Empty JWT claims");
        }

        return false;
    }
}
