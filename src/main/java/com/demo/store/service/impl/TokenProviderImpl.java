package com.demo.store.service.impl;

import com.demo.store.model.JwtData;
import com.demo.store.service.TokenProvider;
import com.demo.store.utils.ObjectUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenProviderImpl implements TokenProvider {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String DATA = "data";
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

    @Override
    public Claims toClaims(String token) {
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
    public JwtData toData(String token) {
        return ObjectUtils.parse(toClaims(token).get(DATA), JwtData.class);
    }

    @Override
    public <T> T parseClaims(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(toClaims(token));
    }

    @Override
    public <T> T parseData(String token, Function<JwtData, T> dataResolver) {
        return dataResolver.apply(toData(token));
    }

    @Override
    public Long getUserId(String token) {
        return parseData(token, JwtData::getUserId);
    }

}
