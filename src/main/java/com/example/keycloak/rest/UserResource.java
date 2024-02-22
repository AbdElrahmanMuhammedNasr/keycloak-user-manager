package com.example.keycloak.rest;

import com.example.keycloak.model.UserVM;
import com.example.keycloak.service.UserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@RestController
@RequiredArgsConstructor
public class UserResource {

    private final UserService userService;


    @GetMapping("/test")
    public String test(){
        return "test";
    }


//  @PostMapping("/login")
//  public Object login(Authentication authentication){
//        return userService.login(authentication);
//    }
    @PostMapping("/create")
    public Object createUser(@RequestBody UserVM user){
        return userService.createUser(user);
    }

    @DeleteMapping("/{id}")
    public Object deleteUser(@PathVariable String id){
        return userService.deleteUser(id);
    }
}
