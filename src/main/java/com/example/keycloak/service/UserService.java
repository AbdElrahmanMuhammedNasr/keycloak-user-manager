package com.example.keycloak.service;

import com.example.keycloak.model.UserVM;
import org.springframework.security.core.Authentication;

public interface UserService {

    Object login(Authentication authentication);
    Object createUser(UserVM user);

    Boolean deleteUser(String Id);
}
