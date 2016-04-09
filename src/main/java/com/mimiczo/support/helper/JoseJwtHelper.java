/*
 * Copyright (c) 2016. by mimiczo
 * All rights reserved.
 */

package com.mimiczo.support.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mimiczo.domain.system.User;
import lombok.extern.slf4j.Slf4j;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Key;

/**
 * com.mimiczo.support.helper
 * Created by mimiczo
 */
@Slf4j
public class JoseJwtHelper implements JwtHelper<User> {

    @Autowired
    private ObjectMapper objectMapper;
    private static JsonWebEncryption jweForEncrypt;

    public JoseJwtHelper() {
        Key key = new AesKey("secretkey".getBytes());
        jweForEncrypt = new JsonWebEncryption();
        jweForEncrypt.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.PBES2_HS256_A128KW);
        jweForEncrypt.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
        jweForEncrypt.setKey(key);
    }

    @Override
    public String create(User user) {
        if(user==null) throw new NullPointerException();

        JwtClaims claims = new JwtClaims();
        claims.setExpirationTimeMinutesInTheFuture(43200);
        //claims.setIssuedAtToNow();
        //claims.setNotBeforeMinutesInThePast(5);
        claims.setAudience("audience");
        claims.setGeneratedJwtId();
        try {
            jweForEncrypt.setPlaintext(objectMapper.writeValueAsString(user));
            return jweForEncrypt.getCompactSerialization();
        } catch (JsonProcessingException | JoseException e) {
            log.debug("{}", e.getMessage());
        }
        return null;
    }
}