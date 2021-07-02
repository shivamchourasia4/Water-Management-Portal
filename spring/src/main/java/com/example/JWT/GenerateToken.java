package com.example.JWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.example.model.UserModel;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class GenerateToken {
    public static Map<String, String> generateJWTToken(UserModel user) {
        long timestamp = System.currentTimeMillis();

        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, constants.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp)).setExpiration(new Date(timestamp + constants.TOKEN_VALIDITY))
                .claim("userID", user.getId()).claim("email", user.getEmail()).claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName()).compact();

        Map<String, String> map = new HashMap<>();
        map.put("token", token);

        return map;
    }
}
