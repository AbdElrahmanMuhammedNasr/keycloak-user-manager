package com.example.keycloak.rest;

import com.example.keycloak.model.UserVM;
import com.example.keycloak.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.ws.rs.core.Response;

@RestController
@RequiredArgsConstructor
public class UserResource {

    private final UserService userService;


    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @PostMapping("/create")
    public UserVM createUser(@RequestBody UserVM user){
        return userService.createUser(user);
    }
}
