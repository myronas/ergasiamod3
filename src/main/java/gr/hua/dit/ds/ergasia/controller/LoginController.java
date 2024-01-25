package gr.hua.dit.ds.ergasia.controller;

import gr.hua.dit.ds.ergasia.dto.LoginRequestDto;
import gr.hua.dit.ds.ergasia.service.LoginService;
import gr.hua.dit.ds.ergasia.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginRequest", new LoginRequestDto());
        return "login"; // Name of the Thymeleaf template for login
    }

    // Example admin-only endpoint URL
    String adminCheckUrl = "http://host.docker.internal:10007/api/admin/users/1";

    @PostMapping("/login")
    public String loginUser(@ModelAttribute LoginRequestDto loginRequestDto, Model model, HttpSession session) {
        ResponseEntity<?> response = loginService.loginUser(loginRequestDto);

        if (response.getStatusCode().is2xxSuccessful()) {
            String jwtToken = (String) response.getBody(); // Now just a plain token string
            tokenService.storeToken(session, jwtToken);

            boolean isAdmin = checkAdminAccess(jwtToken, adminCheckUrl);
            if (isAdmin) {
                return "redirect:/adminMain";
            } else {
                return "redirect:/main";
            }
        } else {
            model.addAttribute("errorMessage", "Login failed");
            return "login";
        }
    }

    private boolean checkAdminAccess(String jwtToken, String adminCheckUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            restTemplate.exchange(adminCheckUrl, HttpMethod.GET, entity, String.class);
            return true; // Success response means user is admin
        } catch (HttpClientErrorException.Forbidden | HttpClientErrorException.Unauthorized e) {
            return false; // Denied access implies a regular user
        }
    }

    @GetMapping("/adminMain")
    public String adminMainPage() {
        return "adminMain"; // This should match the name of your adminMain.html template
    }

}
