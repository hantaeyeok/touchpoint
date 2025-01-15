package com.touchpoint.kh.user.model.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.touchpoint.kh.user.model.dao.UserSocialRepository;
import com.touchpoint.kh.user.model.vo.UserSocial;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SocialLoginServiceImpl implements SocialLoginService {

    private final UserSocialRepository userSocialRepository;
    private final OAuth2AuthorizedClientService authorizedClientService;

    @Override
    public Map<String, Object> socialLogin(String provider, OAuth2User oAuth2User) {
        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());

        // OAuth2AuthorizedClient에서 토큰 정보 가져오기
        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                authenticationToken.getAuthorizedClientRegistrationId(),
                authenticationToken.getName()
        );

        String accessToken = authorizedClient.getAccessToken().getTokenValue();
        String refreshToken = authorizedClient.getRefreshToken() != null ? authorizedClient.getRefreshToken().getTokenValue() : null;
        String tokenExpiry = authorizedClient.getAccessToken().getExpiresAt() != null
                ? authorizedClient.getAccessToken().getExpiresAt().toString() : null;

        // 토큰 정보를 속성에 추가
        attributes.put("access_token", accessToken);
        attributes.put("refresh_token", refreshToken);
        attributes.put("token_expiry", tokenExpiry);

        // Provider-specific 속성 처리
        Map<String, Object> processedAttributes = processAttributes(provider, attributes);

        // 사용자 저장 또는 업데이트
        String providerUserId = (String) processedAttributes.get("id");
        UserSocial userSocial = userSocialRepository.findByProviderAndProviderUserId(provider, providerUserId)
                .map(existingUser -> updateExistingUser(existingUser, processedAttributes))
                .orElseGet(() -> createNewUser(provider, providerUserId, processedAttributes));

        userSocialRepository.save(userSocial);

        // 반환할 데이터 구성
        return Map.of(
                "provider", provider,
                "email", processedAttributes.get("email"),
                "name", processedAttributes.get("name")
        );
    }

    private UserSocial updateExistingUser(UserSocial existingUser, Map<String, Object> attributes) {
        existingUser.setAccessToken((String) attributes.getOrDefault("access_token", existingUser.getAccessToken()));
        existingUser.setRefreshToken((String) attributes.getOrDefault("refresh_token", existingUser.getRefreshToken()));
        existingUser.setTokenExpiry(attributes.get("token_expiry") != null
                ? LocalDate.parse((String) attributes.get("token_expiry"))
                : existingUser.getTokenExpiry());
        return existingUser;
    }

    private UserSocial createNewUser(String provider, String providerUserId, Map<String, Object> attributes) {
        return UserSocial.builder()
                .provider(provider)
                .providerUserId(providerUserId)
                .accessToken((String) attributes.get("access_token"))
                .refreshToken((String) attributes.get("refresh_token"))
                .tokenExpiry(attributes.get("token_expiry") != null
                        ? LocalDate.parse((String) attributes.get("token_expiry"))
                        : null)
                .build();
    }

    private Map<String, Object> processAttributes(String provider, Map<String, Object> attributes) {
        switch (provider.toLowerCase()) {
            case "kakao":
                return processKakao(attributes);
            case "google":
                return processGoogle(attributes);
            case "naver":
                return processNaver(attributes);
            default:
                throw new IllegalArgumentException("Unsupported provider: " + provider);
        }
    }

    private Map<String, Object> processKakao(Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        return Map.of(
                "id", String.valueOf(attributes.get("id")),
                "email", kakaoAccount.get("email"),
                "name", kakaoAccount.get("profile") != null
                        ? ((Map<String, Object>) kakaoAccount.get("profile")).get("nickname")
                        : null,
                "access_token", attributes.get("access_token"),
                "refresh_token", attributes.get("refresh_token"),
                "token_expiry", attributes.get("token_expiry")
        );
    }

    private Map<String, Object> processGoogle(Map<String, Object> attributes) {
        return Map.of(
                "id", attributes.get("sub"),
                "email", attributes.get("email"),
                "name", attributes.get("name"),
                "access_token", attributes.get("access_token"),
                "refresh_token", attributes.get("refresh_token"),
                "token_expiry", attributes.get("token_expiry")
        );
    }

    private Map<String, Object> processNaver(Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return Map.of(
                "id", response.get("id"),
                "email", response.get("email"),
                "name", response.get("name"),
                "access_token", attributes.get("access_token"),
                "refresh_token", attributes.get("refresh_token"),
                "token_expiry", attributes.get("token_expiry")
        );
    }
}