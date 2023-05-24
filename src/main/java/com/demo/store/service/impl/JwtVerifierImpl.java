package com.demo.store.service.impl;

import com.demo.store.model.JwtInfo;
import com.demo.store.service.JwtVerifier;
import com.demo.store.utils.HashingUtils;
import com.demo.store.utils.JsonUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtVerifierImpl implements JwtVerifier {

    /**
     * The secret key for JWT authentication.
     */
    @Value("${auth.jwt.secret-key}")
    private String secretKey;

    /**
     * The token issuer value.
     */
    @Value("${auth.jwt.issuer}")
    private String issuer;

    private static final String DATA = "data";
    public static final String TOKEN_PREFIX = "Bearer ";

    @Override
    public Claims validateAndGetClaimsFromToken(String token) {
        Claims claims = null;
        // Parse JWT with signing key to claims
        token = token.replace(TOKEN_PREFIX, "");
        claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return claims;
    }

    @Override
    public Claims getClaimsFromToken(String token) {
        Claims claims = null;

        try {
            // Parse JWT with signing key to claims
            token = token.replace(TOKEN_PREFIX, "");
            claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
        }

        return claims;
    }

    @Override
    public <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getClaimsFromToken(token);
        return getClaimsFromToken(claims, claimsResolver);
    }

    @Override
    public <T> T getClaimsFromToken(Claims claims, Function<Claims, T> claimsResolver) {
        T result = claimsResolver.apply(claims);
        return result;
    }

    @Override
    public JwtInfo getTokenData(String token) {
        Claims claims = getClaimsFromToken(token);
        return getTokenData(claims);
    }

    @Override
    public JwtInfo getTokenData(Claims claims) {
        JwtInfo result = JsonUtils.convertToObject((String) claims.get(DATA), JwtInfo.class);
        // JwtInfo result = ModelMapperUtils.mergeObject(claims.get(DATA), JwtInfo.class);
        return result;
    }

    @Override
    public String createToken(String subject, Date validFrom, String body, long duration) {
        String result = null;

        // Build claim from inputs
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime exp = now.plusSeconds(duration);
        Map<String, Object> data = new HashMap<>();
        data.put(DATA, body);
        Claims claims = Jwts.claims(data).setSubject(HashingUtils.hashString(subject))
                .setIssuer(issuer).setExpiration(Date.from(exp.toInstant(ZoneOffset.UTC)));

        if (body != null) {
            // Create JWT from signing key
            JwtBuilder builder = Jwts.builder().setClaims(claims)
                    .signWith(SignatureAlgorithm.HS256, secretKey);
            result = builder.compact();
        }
        return result;
    }

    @Override
    public boolean isTokenExpired(String token) {
        try {
            validateAndGetClaimsFromToken(token);
            return false;
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    @Override
    public boolean isValidToken(String token, String subjectValue) {
        String subject = getClaimsFromToken(token, Claims::getSubject);
        return (subject.equals(subjectValue) && !isTokenExpired(token));
    }

    @Override
    public String getUserId(String token) {
        JwtInfo info = getTokenData(token);
        return info.getUserId();
    }

    @Override
    public String getSecretKey() {
        return this.secretKey;
    }

}
