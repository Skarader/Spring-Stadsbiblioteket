package com.example.Gruppuppgift_Statsbibloteket;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.util.Date;

import static io.jsonwebtoken.security.Keys.secretKeyFor;

@Component
public class JWTUtil {
    // generera en nyckel för JWT signering
    private SecretKey key = secretKeyFor(SignatureAlgorithm.HS256);
    // gör om vår nyckel till en base64 kodad sträng
    private final String SECRET_KEY = Encoders.BASE64.encode(key.getEncoded());

    // skapar en token
    public String generateToken(String userName) {

        // Jwts.builder lägger till de fält vi vill ha med i vår token och signerar med
        // vår nyckel
        String token = Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        // OBS .signWith() är deprecated och man föreslår att använda en parserBuilder
        // för att hantera signering ist.
        // Fördelen med parserBuilder är att den är threadsafe och mutable, men inget
        // som kommer göra skillnad i våra exempel.
        // men helt klart värt att kolla upp för framtida projekt

        return token;
    }

    public String extractUserName(String token) {

        String name = Jwts.parser()
                .setSigningKey(SECRET_KEY) // deprecated av samma anledning som ovan
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        return name;
    }

    // kollar om token är inom giltighetstiden
    private boolean isTokenExpired(String token) {

        Date expiration = Jwts.parser()
                .setSigningKey(SECRET_KEY) // deprecated av samma anledning som ovan
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        return expiration.before(new Date());
    }

    // kollar om token är giltig
    public boolean isValid(String token, String userName) {

        // HR ÄR BOVEN FRÅN LEKTIONEN. Jag hade skickat in userName ist för token i
        // metoden
        final String extractedName = extractUserName(token); // final String extractedName = extractUserName(userName);
        return extractedName.equals(userName) && !isTokenExpired(token);

    }
}
