//package com.zerobase.cms.config;
//
//import com.zerobase.cms.domain.common.UserType;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import org.springframework.beans.factory.annotation.Value;
//
//public class JwtAuthenticationProvider {
//    @Value("${}")
//    private String secretKey;
//
//    private final long tokenValidTime = 1000L * 60 * 60 * 24;
//
//    private String createToken(String userPk, Long id, UserType userType){
//        Claims claims = Jwts.claims().setSubject()
//    }
//
//}
