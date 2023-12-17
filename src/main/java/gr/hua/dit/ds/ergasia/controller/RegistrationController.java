package gr.hua.dit.ds.ergasia.controller;

import gr.hua.dit.ds.ergasia.entity.User;
import gr.hua.dit.ds.ergasia.exception.UsernameAlreadyExistsException;
import gr.hua.dit.ds.ergasia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUserAccount(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes, Model model) {
        try {
            userService.registerNewUserAccount(user);
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful! You can now login.");
            return "redirect:/login";
        } catch (UsernameAlreadyExistsException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("user", user);
            return "register";
        }
    }
}
