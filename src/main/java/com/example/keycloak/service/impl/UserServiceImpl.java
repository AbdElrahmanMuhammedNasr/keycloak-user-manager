package com.example.keycloak.service.impl;

import com.example.keycloak.model.UserVM;
import com.example.keycloak.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    Keycloak keycloak;

    @Value("${c-keycloak.realm}")
    public  String realm ="medical";
    @Override
    public UserVM createUser(UserVM user) {
         RealmResource medical = keycloak.realm(realm);
        UsersResource users = medical.users();

        UserRepresentation userRepresentation  = new UserRepresentation();
        userRepresentation.setUsername(user.getFirstName());
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setFirstName(user.getFirstName());
        userRepresentation.setLastName(user.getLastName());
        userRepresentation.setEnabled(true);

        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("location", user.getLocation());
        userRepresentation.setAttributes(attributes);

        userRepresentation.setRealmRoles(List.of("USER_ROLE"));
        userRepresentation.setGroups(List.of("USER_GROUP"));

        CredentialRepresentation credentials = new CredentialRepresentation();
        credentials.setValue(user.getPassword());
        credentials.setTemporary(false);
        credentials.setType(CredentialRepresentation.PASSWORD);
        userRepresentation.setCredentials(Arrays.asList(credentials));


        Response response = users.create(userRepresentation);
        return  user;
    }
}
