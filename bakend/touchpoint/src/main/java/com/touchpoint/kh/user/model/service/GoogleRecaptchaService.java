package com.touchpoint.kh.user.model.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleRecaptchaService {

    private static final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
    private static final String SECRET_KEY = "6Lc-m7QqAAAAAGnwC5OIFaJxiHlknAVpNvuS9Xnp"; // 발급받은 Secret Key

    public boolean verifyRecaptcha(String recaptchaToken) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = Map.of(
            "secret", SECRET_KEY,
            "response", recaptchaToken
        );

        // Google API 호출 및 응답 처리
        Map<String, Object> response = restTemplate.postForObject(RECAPTCHA_VERIFY_URL, params, Map.class);
        return response != null && (Boolean) response.get("success");
    }
}
