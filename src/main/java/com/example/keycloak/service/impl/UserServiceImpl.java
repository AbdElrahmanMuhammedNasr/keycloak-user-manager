package com.example.keycloak.service.impl;

import com.example.keycloak.model.UserVM;
import com.example.keycloak.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    Keycloak keycloak;

    @Autowired
    KeycloakSecurityContext keycloakSecurityContext;

    @Value("${c-keycloak.realm}")
    public  String realm ="medical";

    @Value("${c-keycloak.client}")
    static String clientId = "user-manager-client";

    @Value("${c-keycloak.secret}")
    static String clientSecret = "ioSR6KSghanZWnbzeWwT1s9JGB86ivn0";

    @Override
    public Object login(Authentication authentication) {
        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) authentication;
        RefreshableKeycloakSecurityContext context = (RefreshableKeycloakSecurityContext) token.getCredentials();
        AccessToken accessToken = context.getToken();

        return accessToken;
    }

    @Override
    public Object createUser(UserVM user) {
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
        return  response;
    }

    @Override
    public Boolean deleteUser(String Id) {
        RealmResource medical = keycloak.realm(realm);
        medical.users().get(Id).remove();
        return Boolean.TRUE;
    }
}
