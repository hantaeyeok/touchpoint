package com.touchpoint.kh.user.model.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoogleRecaptchaService {

    @Value("${google.recaptcha.secret}")
    private String recaptchaSecret;

    @Value("${google.recaptcha.url}")
    private String recaptchaUrl;

    public boolean verifyRecaptcha(String recaptchaToken) {
        RestTemplate restTemplate = new RestTemplate();

        // 요청 파라미터 설정
        Map<String, String> params = Map.of(
            "secret", recaptchaSecret,
            "response", recaptchaToken
        );

        try {
            // Google API 호출 및 응답 처리
            Map<String, Object> response = restTemplate.postForObject(recaptchaUrl, params, Map.class);

            // 검증 성공 여부 반환
            return response != null && Boolean.TRUE.equals(response.get("success"));
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Google API 요청 실패 처리
            System.err.println("Error during reCAPTCHA validation: " + e.getMessage());
            return false;
        } catch (Exception e) {
            // 기타 예외 처리
            System.err.println("Unexpected error during reCAPTCHA validation: " + e.getMessage());
            return false;
        }
    }
}

