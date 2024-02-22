package com.example.keycloak.service;

import com.example.keycloak.model.UserVM;

public interface UserService {

    UserVM createUser(UserVM user);
}
