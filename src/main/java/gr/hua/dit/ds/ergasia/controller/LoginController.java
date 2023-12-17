package gr.hua.dit.ds.ergasia.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.util.Optional;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model, @RequestParam Optional<String> error) {
        if (error.isPresent()) {
            model.addAttribute("errorMessage", "Invalid login attempt.");
        }
        return "login";
    }


}
