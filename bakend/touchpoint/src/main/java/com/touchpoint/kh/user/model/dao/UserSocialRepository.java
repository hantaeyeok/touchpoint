package com.touchpoint.kh.user.model.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.touchpoint.kh.user.model.vo.UserSocial;

@Repository
public interface UserSocialRepository extends JpaRepository<UserSocial, String> {

    Optional<UserSocial> findByProviderAndProviderUserId(String provider, String providerUserId);
    UserSocial findByProviderUserId(String providerUserId);

}
