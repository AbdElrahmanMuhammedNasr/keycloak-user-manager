package com.example.keycloak.config;

import com.example.keycloak.rest.UserResource;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    @Value("${c-keycloak.server-url}")
    static String serverUrl ="http://localhost:6521/auth";

   @Value("${c-keycloak.realm}")
    public  static String realm = "medical";

    @Value("${c-keycloak.client}")
    static String clientId = "user-manager-client";

    @Value("${c-keycloak.secret}")
    static String clientSecret = "ioSR6KSghanZWnbzeWwT1s9JGB86ivn0";



    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
    }
}
//
//   build.realms().findAll().forEach(r-> System.out.println(r.getRealm()));
//        System.out.println(
//        build.realms().realm("medical").users().search("root").getFirst().getId()
//        );
//
//                System.out.println(
////                build.realms().realm("medical").clients().get("13a073f6-4c27-4c69-8a41-de10dd568b3d").getUserSessions(0,10).getFirst().getUserId()
//);
//        build.realms().realm("medical").users().get("f1d67eff-5d81-4ee3-9a3a-7ad526527d5d").getUserSessions().forEach(s-> {
//
//        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
//            System.out.println(s.getIpAddress());
//        System.out.println(s.getStart());
//        System.out.println(s.getLastAccess());
//        System.out.println(s.getUsername());
//        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
//            build.realms().realm("medical").deleteSession(s.getId());
//        });