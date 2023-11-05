package com.springBoard.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springBoard.user.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class TokenManager {
    private static final String SECRET_KEY = "TEST_SECRET_KEY_TEST_SECRET_KEY_TEST_SECRET_KEY_TEST_SECRET_KEY";

    public static String createToken(Object subject){
        ObjectMapper objectMapper = new ObjectMapper();
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        byte[] secreteKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(secreteKeyBytes, signatureAlgorithm.getJcaName());

        try{
            return Jwts.builder()
                    .setSubject(objectMapper.writeValueAsString(subject))
                    .signWith(signingKey, signatureAlgorithm)
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                    .compact();
        } catch (Exception e){
            throw new RuntimeException("Token 생성 오류");
        }
    }

    public static String getSubject(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }


}
