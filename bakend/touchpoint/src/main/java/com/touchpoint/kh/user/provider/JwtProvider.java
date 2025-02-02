package com.touchpoint.kh.user.provider;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

	@Value("${secret-key}")
	private String secrectKey;

    // 사용자 ID와 활성 토큰 매핑
    private final ConcurrentHashMap<String, String> activeTokens = new ConcurrentHashMap<>();

	public String create(String userId, String role) {
		
		Date expiredData = Date.from(Instant.now().plus(1,ChronoUnit.HOURS));
		Key key =Keys.hmacShaKeyFor(secrectKey.getBytes(StandardCharsets.UTF_8));
		
		String jwt = Jwts.builder()
				.signWith(key, SignatureAlgorithm.HS256)
				.setSubject(userId)
				.setIssuedAt(new Date())
				.setExpiration(expiredData)
	            .claim("role", role)
				.compact();
        activeTokens.put(userId, jwt);

		return jwt;
	}
	
//	public String validata(String jwt) {
//		
//		String subject = null;
//		Key key = Keys.hmacShaKeyFor(secrectKey.getBytes(StandardCharsets.UTF_8));
//		
//		try {
//			
//			subject = Jwts.parserBuilder()
//					.setSigningKey(key)
//					.build()
//					.parseClaimsJws(jwt)
//					.getBody()
//					.getSubject();
//			if (jwt.equals(activeTokens.get(subject))) {
//	            return subject; // 사용자 ID 반환
//	        }			 
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//		
//		return subject;
//	}

	public Map<String, String> validata(String jwt) {
	    Key key = Keys.hmacShaKeyFor(secrectKey.getBytes(StandardCharsets.UTF_8));

	    try {
	        var claims = Jwts.parserBuilder()
	                .setSigningKey(key)
	                .build()
	                .parseClaimsJws(jwt)
	                .getBody();

	        String subject = claims.getSubject(); // 사용자 ID 추출
	        String role = claims.get("role", String.class); // 역할(Role) 추출

	        if (jwt.equals(activeTokens.get(subject))) {
	            return Map.of("userId", subject, "role", role); // 사용자 ID와 Role 반환
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null; // 검증 실패 시 null 반환
	}

	    // 기존 토큰 무효화
	    public void invalidate(String userId) {
	        activeTokens.remove(userId);
	    }
}
