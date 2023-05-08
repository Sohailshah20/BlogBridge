package com.shah.blogbridge.service;

import com.shah.blogbridge.user.User;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomOidcUserService extends OidcUserService {

    private UserService userService;

    public CustomOidcUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        Map attributes = oidcUser.getAttributes();
        User userInfo = new User();
        userInfo.setEmail((String) attributes.get("email"));
        userInfo.setAvatar((String) attributes.get("picture"));
        userInfo.setName((String) attributes.get("name"));
        userService.saveUser(userInfo);
        return oidcUser;
    }
}
