package com.demo.store.service;

import com.demo.store.model.JwtInfo;
import io.jsonwebtoken.Claims;
import java.util.Date;
import java.util.function.Function;

public interface JwtVerifier {

    /**
     * Get claims from token with validating expire time.
     *
     * @param token the JWT token
     * @return the Claims of JWT token
     */
    public Claims validateAndGetClaimsFromToken(String token);

    /**
     * Get claims from token without validating expire time.
     *
     * @param token the JWT token
     * @return the Claims of JWT token
     */
    public Claims getClaimsFromToken(String token);

    /**
     * Get claims from token.
     *
     * @param token          the JWT token
     * @param claimsResolver the claims resolver function
     * @return the JWT field data
     */
    public <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver);

    /**
     * Get claims from token.
     *
     * @param claims         the JWT claims
     * @param claimsResolver the claims resolver function
     * @return the JWT field data
     */
    public <T> T getClaimsFromToken(Claims claims, Function<Claims, T> claimsResolver);

    /**
     * Get custom data in JWT token
     *
     * @param token the JWT token
     * @return the JWT custom field data
     */
    public JwtInfo getTokenData(String token);

    /**
     * Get custom data in JWT token
     *
     * @param claims the JWT claims
     * @return the JWT custom field data
     */
    public JwtInfo getTokenData(Claims claims);

    /**
     * Create JWT token with HS256 signature.
     *
     * @param subject   the subject
     * @param validFrom the token valid from date
     * @param body      the body JSON string
     * @param duration  the token expired duration
     * @return the token string
     */
    public String createToken(String subject, Date validFrom, String body, long duration);

    /**
     * Check if the request token is expired.
     *
     * @param token the request token
     * @return boolean<br> true -> valid token<br> false -> invalid token
     */
    public boolean isTokenExpired(String token);

    /**
     * Check if the request token is invalid.
     *
     * @param token        the request token
     * @param subjectValue the value of subject
     * @return boolean<br> true -> valid token<br> false -> invalid token
     */
    public boolean isValidToken(String token, String subjectValue);

    /**
     * Get user id
     *
     * @param token the request token
     * @return user id
     */
    public String getUserId(String token);

    /**
     * Get JWT secret key
     *
     * @return secret key
     */
    public String getSecretKey();

}
