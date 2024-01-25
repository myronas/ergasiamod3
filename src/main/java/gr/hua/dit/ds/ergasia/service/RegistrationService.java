package gr.hua.dit.ds.ergasia.service;

import gr.hua.dit.ds.ergasia.dto.RegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.logging.Logger;


@Service
public class RegistrationService {
    private static final Logger logger = Logger.getLogger(RegistrationService.class.getName());
    private final RestTemplate restTemplate;
    private final String backendUrl = "http://host.docker.internal:10007/api/register";

    @Autowired
    public RegistrationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<?> registerUser(RegistrationDto registrationDto) {
        logger.info("Registering user: " + registrationDto.getUsername());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RegistrationDto> request = new HttpEntity<>(registrationDto, headers);

        logger.info("Sending request to backend service for user registration ."+ backendUrl);
        ResponseEntity<?> response = restTemplate.postForEntity(backendUrl, request, String.class);

        logger.info("Response received from backend service: " + response.getStatusCode());
        return response;
    }
}

