package com.zerobase.cms.config;

import com.zerobase.cms.domain.common.UserType;
import com.zerobase.cms.domain.common.UserVo;
import com.zerobase.cms.util.Aes256Util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;

public class JwtAuthenticationProvider {

    @Value("${jwt.secret_key}")
    private String secretKey;

    private final long tokenValidTime = 1000L * 60 * 60 * 24;

    public String createToken(String userPk, Long id, UserType userType) {
        Claims claims = Jwts.claims().setSubject(Aes256Util.encrypt(userPk))
            .setId(Aes256Util.encrypt(id.toString()));
        claims.put("roles", userType);
        Date now = new Date();
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + tokenValidTime))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public UserVo getUserVo(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return new UserVo(Long.valueOf(Aes256Util.decrypt(claims.getId())),
            Aes256Util.decrypt(claims.getSubject()));
    }
}
