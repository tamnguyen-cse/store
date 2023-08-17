package com.demo.store.service;

import com.demo.store.model.JwtData;
import io.jsonwebtoken.Claims;
import java.util.function.Function;

public interface TokenProvider {

    /**
     * Parse token and get claims without signature verification.
     *
     * @param token the JWT token
     * @return the Claims of JWT token
     */
    Claims toClaims(String token);

    /**
     * Parse token and get token metadata without signature verification.
     *
     * @param token the JWT token
     * @return the JWT custom field data
     */
    JwtData toData(String token);

    /**
     * Parse token and get data with claims resolver function.
     *
     * @param token          the JWT token
     * @param claimsResolver the claims resolver function
     * @return the JWT field data
     */
    <T> T parseClaims(String token, Function<Claims, T> claimsResolver);

    /**
     * Parse claims and get data with claims resolver function.
     *
     * @param token        the JWT token
     * @param dataResolver the JWT data resolver function
     * @return the JWT field data
     */
    <T> T parseData(String token, Function<JwtData, T> dataResolver);

    /**
     * Parse token and get user id from token metadata
     *
     * @param token the request token
     * @return customer id
     */
    Long getUserId(String token);

}
