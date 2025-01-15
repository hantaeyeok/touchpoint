package com.touchpoint.kh.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

	@Value("${secret-key}")
	private String secrectKey;
	
	public String create(String userId) {
		
		Date expiredData = Date.from(Instant.now().plus(1,ChronoUnit.HOURS));
		Key key =Keys.hmacShaKeyFor(secrectKey.getBytes(StandardCharsets.UTF_8));
		
		String jwt = Jwts.builder()
				.signWith(key, SignatureAlgorithm.HS256)
				.setSubject(userId).setIssuedAt(new Date()).setExpiration(expiredData)
				.compact();
		
		return jwt;
	}
	
	public String validata (String jwt) {
		
		String subject = null;
		Key key = Keys.hmacShaKeyFor(secrectKey.getBytes(StandardCharsets.UTF_8));
		
		try {
			
			subject = Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(jwt)
					.getBody()
					.getSubject();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return subject;
	}
}
