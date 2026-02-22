package com.example;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class DemoController {

    @RequestMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }

    @RequestMapping("/user-details")
    public Map<String, Object> getAllDetails(@AuthenticationPrincipal OAuth2User principal) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        return principal.getAttributes();
    }
}
