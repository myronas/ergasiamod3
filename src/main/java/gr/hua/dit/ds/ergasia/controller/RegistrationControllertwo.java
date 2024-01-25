package gr.hua.dit.ds.ergasia.controller;

import gr.hua.dit.ds.ergasia.dto.RegistrationDto;
import gr.hua.dit.ds.ergasia.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
public class RegistrationControllertwo {

    @Autowired
    private RegistrationService registrationService;



    @GetMapping("/register")
    public String showRegistrationForm(Model model) {

        model.addAttribute("user", new RegistrationDto());
        return "register";
    }


    @PostMapping("/register")
    public String registerUser(@ModelAttribute RegistrationDto registrationDto, Model model) {
        ResponseEntity<?> response = registrationService.registerUser(registrationDto);


        if (response.getStatusCode() == HttpStatus.OK) {
            model.addAttribute("message", "Registration successful");
            return "index";
        } else {
            model.addAttribute("errorMessage", "Registration failed");
            return "register";
        }
    }

}
