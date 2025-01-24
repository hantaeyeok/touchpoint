package com.touchpoint.kh.user.model.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@Service
public class GoogleRecaptchaService {

    // 테스트 목적으로 하드코딩된 reCAPTCHA 비밀 키
    private static final String RECAPTCHA_SECRET = "6LcZWL4qAAAAACIMv1BDZo4mvO1AOaAykl67bDej"; // <- 여기에 비밀키를 입력하세요
    private static final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public boolean verifyRecaptcha(String recaptchaToken) {
        RestTemplate restTemplate = new RestTemplate();

        // 요청 데이터 생성
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("secret", RECAPTCHA_SECRET); // 하드코딩된 비밀 키
        requestBody.add("response", recaptchaToken); // 클라이언트에서 받은 토큰

        // 요청 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 요청 엔티티 생성
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            // reCAPTCHA 검증 요청
            ResponseEntity<Map> response = restTemplate.exchange(
                    RECAPTCHA_VERIFY_URL, HttpMethod.POST, requestEntity, Map.class);

            // 응답 본문 처리
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null) {
                boolean success = (Boolean) responseBody.get("success"); // 성공 여부
                String hostname = (String) responseBody.get("hostname"); // 요청 도메인 확인 (선택적)

                log.info("reCAPTCHA 검증 결과 - 성공 여부: {}, 도메인: {}", success, hostname);
                return success;
            }
        } catch (Exception e) {
            log.error("reCAPTCHA 검증 실패: {}", e.getMessage());
        }

        return false; // 기본적으로 실패로 간주
    }
}
