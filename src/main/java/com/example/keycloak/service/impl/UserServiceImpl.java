package com.example.keycloak.service.impl;

import com.example.keycloak.model.UserVM;
import com.example.keycloak.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.admin.client.token.TokenService;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Value("${c-keycloak.client}")
    static String clientId = "user-manager-client";

    @Value("${c-keycloak.secret}")
    static String clientSecret = "ioSR6KSghanZWnbzeWwT1s9JGB86ivn0";

    @Override
    public Object login() {
        Keycloak keycloak = Keycloak.getInstance("http://localhost:6521/auth", realm, "root", "root", clientId, clientSecret);
        AccessTokenResponse accessToken = keycloak.tokenManager().getAccessToken();
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

    @Override
    public Object updateUserRole(String userId) {
        RealmResource medical = keycloak.realm(realm);
        UsersResource users = medical.users();
        UserResource user = users.get(userId);

        UserRepresentation userUpdate = user.toRepresentation();

        RoleRepresentation role = medical.roles().get("USER_ROLE").toRepresentation();
        users.get(userId).roles().realmLevel().add(Arrays.asList(role));

        users.get(userId).update(userUpdate);
        return true;
    }

    @Override
    public Object removeUserRole(String userId) {
        RealmResource medical = keycloak.realm(realm);
        UsersResource users = medical.users();
        UserResource user = users.get(userId);
        UserRepresentation userUpdate = user.toRepresentation();

        RoleRepresentation role = medical.roles().get("ADMIN_ROLE").toRepresentation();
        users.get(userId).roles().realmLevel().remove(Arrays.asList(role));
        users.get(userId).update(userUpdate);
        return true;
    }
}
