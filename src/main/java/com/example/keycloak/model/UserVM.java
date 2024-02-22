package com.example.keycloak.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
 @FieldDefaults(level = AccessLevel.PRIVATE)
public class UserVM {
    String firstName;
    String lastName;
    String email;
    String password;
    List<String> location;
}
