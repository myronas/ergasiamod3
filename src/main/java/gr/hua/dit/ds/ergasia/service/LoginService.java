package gr.hua.dit.ds.ergasia.service;

import gr.hua.dit.ds.ergasia.dto.LoginRequestDto;
import gr.hua.dit.ds.ergasia.dto.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LoginService {
    private final RestTemplate restTemplate;
    private final String backendLoginUrl = "http://host.docker.internal:10007/api/login";

    @Autowired
    public LoginService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<?> loginUser(LoginRequestDto loginRequestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LoginRequestDto> request = new HttpEntity<>(loginRequestDto, headers);
        ResponseEntity<TokenResponse> response = restTemplate.postForEntity(backendLoginUrl, request, TokenResponse.class);
        return ResponseEntity.ok(response.getBody().getToken());
    }
}

