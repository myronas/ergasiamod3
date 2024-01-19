//package gr.hua.dit.ds.ergasia.controller;
//
//import gr.hua.dit.ds.ergasia.dto.ErrorResponseDto;
//import gr.hua.dit.ds.ergasia.dto.JwtResponseDto;
//import gr.hua.dit.ds.ergasia.dto.LoginRequestDto;
//import gr.hua.dit.ds.ergasia.dto.UserRegistrationDto;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RestTemplate;
//
//@Controller
//public class UserAuthController {
//
//    private final RestTemplate restTemplate;
//    private final String backendBaseUrl = "http://localhost:9090/api";
//
//    @Autowired
//    public UserAuthController(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//    @GetMapping("/")
//    public String index() {
//        return "index";  // This will return src/main/resources/static/index.html
//    }
//
////    @GetMapping("/register")
////    public String showRegistrationForm(Model model) {
////        // Add model attributes if needed
////        return "register"; // Name of the HTML view for registration
////    }
////
////    @PostMapping("/register")
////    public String registerUser(@ModelAttribute UserRegistrationDto registrationDto, Model model) {
////        ResponseEntity<?> response = restTemplate.postForEntity(backendBaseUrl + "/register", registrationDto, ResponseEntity.class);
////
////        if (response.getStatusCode() == HttpStatus.OK) {
////            // Registration successful
////            model.addAttribute("message", "Registration successful! You can now login.");
////            return "redirect:/login";  // Redirect to login page
////        } else {
////            // Handle registration error
////            ErrorResponseDto errorResponse = (ErrorResponseDto) response.getBody();
////            model.addAttribute("error", errorResponse.getErrorMessage());
////            return "register";  // Stay on the registration page
////        }
////    }
////
////    @GetMapping("/login")
////    public String showLoginForm(Model model) {
////        // Add model attributes if needed
////        return "login"; // Name of the HTML view for login
////    }
////
////    @PostMapping("/login")
////    public String loginUser(@ModelAttribute LoginRequestDto loginRequestDto, Model model) {
////        ResponseEntity<JwtResponseDto> response = restTemplate.postForEntity(backendBaseUrl + "/login", loginRequestDto, JwtResponseDto.class);
////        // Handle response and add attributes to model
////        return "loginResult"; // Name of the view to show after login
////    }
//}
