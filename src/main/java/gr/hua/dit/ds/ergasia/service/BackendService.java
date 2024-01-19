//package gr.hua.dit.ds.ergasia.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//public class BackendService {
//
//    private final RestTemplate restTemplate;
//
//    @Autowired
//    public BackendService(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    public String loginUser(String username, String password) {
//        String url = "http://app:9090/api/login";
//
//        // Create request body
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        String requestJson = "{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}";
//        HttpEntity<String> request = new HttpEntity<>(requestJson, headers);
//
//        // Make the POST request
//        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
//        return response.getBody();
//    }
//}
//
