package com.tecacet.demo.envers.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public String getCurrentUser() {
        if (SecurityContextHolder.getContext() == null
                || SecurityContextHolder.getContext().getAuthentication() == null) {
            return "ADMIN";
        }
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
