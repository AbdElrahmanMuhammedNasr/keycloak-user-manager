package com.example.keycloak.service;

import com.example.keycloak.model.UserVM;
import org.springframework.security.core.Authentication;

public interface UserService {

    Object login();
    Object createUser(UserVM user);

    Boolean deleteUser(String Id);
    Object updateUserRole(String userId);
    Object removeUserRole(String userId);
}
