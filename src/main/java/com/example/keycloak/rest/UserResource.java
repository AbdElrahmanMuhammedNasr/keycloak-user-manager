package com.example.keycloak.rest;

import com.example.keycloak.model.UserVM;
import com.example.keycloak.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


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
//        return userService.login();
//    }
    @PostMapping("/create")
    public Object createUser(@RequestBody UserVM user){
        return userService.createUser(user);
    }

    @DeleteMapping("/{id}")
    public Object deleteUser(@PathVariable String id){
        return userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public Object updateUserRole(@PathVariable String id){
        return userService.updateUserRole(id);
    }

    @DeleteMapping("/{id}/role")
    public Object removeUserRole(@PathVariable String id){
        return userService.removeUserRole(id);
    }
}
