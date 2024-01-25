package gr.hua.dit.ds.ergasia.controller;

import gr.hua.dit.ds.ergasia.dto.ApiResponse;
import gr.hua.dit.ds.ergasia.dto.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/main")
public class MainController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpSession session;

    private final String backendBaseUrl = "http://host.docker.internal:10007/api/main";

    @GetMapping
    public String mainPage(Model model) {
        ResponseEntity<ApiResponse<List<ItemDTO>>> response = restTemplate.exchange(
                backendBaseUrl + "/my-items",
                HttpMethod.GET,
                new HttpEntity<>(createHeaders()),
                new ParameterizedTypeReference<ApiResponse<List<ItemDTO>>>() {}
        );

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            ApiResponse<List<ItemDTO>> apiResponse = response.getBody();
            if (apiResponse.isSuccess() && apiResponse.getData() != null) {
                model.addAttribute("items", apiResponse.getData());
            } else {
                model.addAttribute("errorMessage", "No items found or an error occurred.");
            }
        } else {
            // Handle error case
            // You may add an error message to the model to display in the UI
            model.addAttribute("errorMessage", "Error retrieving items: " + response.getStatusCode());
        }
        return "main";
    }

    @PostMapping("/items")
    public String createItem(@ModelAttribute ItemDTO item, RedirectAttributes redirectAttributes) {
        ResponseEntity<String> response = restTemplate.exchange(
                backendBaseUrl + "/items",
                HttpMethod.POST,
                new HttpEntity<>(item, createHeaders()),
                String.class
        );

        handleResponse(redirectAttributes, response);
        return "redirect:/main";
    }


    @PostMapping("/items/update")
    public String updateItem(@ModelAttribute ItemDTO item, RedirectAttributes redirectAttributes) {
        HttpHeaders headers = createHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ItemDTO> entity = new HttpEntity<>(item, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                backendBaseUrl + "/items/" + item.getId(),
                HttpMethod.PUT,
                entity,
                String.class
        );

        handleResponse(redirectAttributes, response);
        return "redirect:/main";
    }


    @PostMapping("/items/delete")
    public String deleteItem(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
        ResponseEntity<String> response = restTemplate.exchange(
                backendBaseUrl + "/items/" + id,
                HttpMethod.DELETE,
                new HttpEntity<>(createHeaders()),
                String.class
        );

        handleResponse(redirectAttributes, response);
        return "redirect:/main";
    }

    @PostMapping("/items/hide")
    public String hideItem(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
        ResponseEntity<String> response = restTemplate.exchange(
                backendBaseUrl + "/items/hide/" + id,
                HttpMethod.PATCH,
                new HttpEntity<>(createHeaders()),
                String.class
        );

        handleResponse(redirectAttributes, response);
        return "redirect:/main";
    }

    @PostMapping("/items/lift")
    public String liftHiddenItems(RedirectAttributes redirectAttributes) {
        ResponseEntity<String> response = restTemplate.exchange(
                backendBaseUrl + "/items/lift",
                HttpMethod.PATCH,
                new HttpEntity<>(createHeaders()),
                String.class
        );

        handleResponse(redirectAttributes, response);
        return "redirect:/main";
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String token = (String) session.getAttribute("AuthToken");
        if (token != null) {
            headers.setBearerAuth(token);
        }
        return headers;
    }

    private void handleResponse(RedirectAttributes redirectAttributes, ResponseEntity<String> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            redirectAttributes.addFlashAttribute("successMessage", "Operation successful.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Operation failed: " + response.getStatusCode());
        }
    }
    @GetMapping("/new-item")
    public String showCreateItemForm(Model model) {
        model.addAttribute("item", new ItemDTO());
        return "itemForm";
    }
    @GetMapping("/items/edit/{id}")
    public String showUpdateItemForm(@PathVariable Integer id, Model model) {
        ResponseEntity<ApiResponse<ItemDTO>> response = restTemplate.exchange(
                backendBaseUrl + "/items/" + id,
                HttpMethod.GET,
                new HttpEntity<>(createHeaders()),
                new ParameterizedTypeReference<ApiResponse<ItemDTO>>() {}
        );

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null && response.getBody().getData() != null) {
            model.addAttribute("item", response.getBody().getData());
            return "itemEditForm";
        } else {
            // Error handling
            return "redirect:/main";
        }
    }




}
