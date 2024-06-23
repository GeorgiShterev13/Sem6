package org.example.user.business.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Auth0Service {

    @Value("${auth0.domain}")
    private String auth0Domain;

    @Value("${auth0.apiClientId}")
    private String apiClientId;

    @Value("${auth0.apiClientSecret}")
    private String apiClientSecret;

    public void deleteUserFromAuth0(String userId) {
        String token = getManagementApiToken();
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        restTemplate.exchange("https://" + auth0Domain + "/api/v2/users/" + userId,
                HttpMethod.DELETE, entity, String.class);
    }

    private String getManagementApiToken() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");

        String body = String.format("{\"client_id\":\"%s\",\"client_secret\":\"%s\",\"audience\":\"https://%s/api/v2/\",\"grant_type\":\"client_credentials\"}",
                apiClientId, apiClientSecret, auth0Domain);

        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<TokenResponse> response = restTemplate.postForEntity("https://" + auth0Domain + "/oauth/token", request, TokenResponse.class);

        return response.getBody().getAccessToken();
    }

    private static class TokenResponse {
        private String access_token;

        public String getAccessToken() {
            return access_token;
        }
    }
}
