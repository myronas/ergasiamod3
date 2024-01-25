//package gr.hua.dit.ds.ergasia.service;
//
//import gr.hua.dit.ds.ergasia.dto.ItemDTO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.client.HttpClientErrorException;
//
//import jakarta.servlet.http.HttpSession;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//@Service
//public class ItemService {
//    private final RestTemplate restTemplate;
//    private final String backendBaseUrl = "http://host.docker.internal:10007/api/main";
//
//    @Autowired
//    public ItemService(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    public String createItem(ItemDTO item, HttpSession session) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBearerAuth(getAuthToken(session));
//
//        HttpEntity<ItemDTO> entity = new HttpEntity<>(item, headers);
//        try {
//            ResponseEntity<String> response = restTemplate.postForEntity(
//                    backendBaseUrl + "/items", entity, String.class);
//            return response.getBody();
//        } catch (HttpClientErrorException e) {
//            return "Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString();
//        }
//    }
//
//    public ItemDTO getItemById(Integer id) {
//        try {
//            ResponseEntity<ItemDTO> response = restTemplate.getForEntity(
//                    backendBaseUrl + "/items/" + id, ItemDTO.class);
//            return response.getBody();
//        } catch (HttpClientErrorException e) {
//            // Handle exception or return null
//            return null;
//        }
//    }
//
//    public String updateItem(Integer id, ItemDTO item, HttpSession session) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBearerAuth(getAuthToken(session));
//
//        HttpEntity<ItemDTO> entity = new HttpEntity<>(item, headers);
//        try {
//            restTemplate.exchange(
//                    backendBaseUrl + "/items/" + id, HttpMethod.PUT, entity, Void.class);
//            return "Item updated successfully";
//        } catch (HttpClientErrorException e) {
//            return "Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString();
//        }
//    }
//
//    public String deleteItem(Integer id, HttpSession session) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(getAuthToken(session));
//
//        HttpEntity<?> entity = new HttpEntity<>(headers);
//        try {
//            restTemplate.exchange(
//                    backendBaseUrl + "/items/" + id, HttpMethod.DELETE, entity, Void.class);
//            return "Item deleted successfully";
//        } catch (HttpClientErrorException e) {
//            return "Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString();
//        }
//    }
//
//    public String hideItem(Integer id, HttpSession session) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(getAuthToken(session));
//
//        HttpEntity<?> entity = new HttpEntity<>(headers);
//        try {
//            restTemplate.exchange(
//                    backendBaseUrl + "/items/hide/" + id, HttpMethod.PATCH, entity, Void.class);
//            return "Item hidden successfully";
//        } catch (HttpClientErrorException e) {
//            return "Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString();
//        }
//    }
//
//    public String liftHiddenItems(HttpSession session) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(getAuthToken(session));
//
//        HttpEntity<?> entity = new HttpEntity<>(headers);
//        try {
//            restTemplate.exchange(
//                    backendBaseUrl + "/items/lift", HttpMethod.PATCH, entity, Void.class);
//            return "Hidden items lifted successfully";
//        } catch (HttpClientErrorException e) {
//            return "Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString();
//        }
//    }
//
//    public List<ItemDTO> getMyItems(HttpSession session) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(getAuthToken(session));
//
//        try {
//            ResponseEntity<ItemDTO[]> response = restTemplate.exchange(
//                    backendBaseUrl + "/my-items", HttpMethod.GET, new HttpEntity<>(headers), ItemDTO[].class);
//            return Arrays.asList(response.getBody());
//        } catch (HttpClientErrorException e) {
//            // Handle exception or return an empty list
//            return Collections.emptyList();
//        }
//    }
//
//    private String getAuthToken(HttpSession session) {
//        return (String) session.getAttribute("AuthToken");
//    }
//}
