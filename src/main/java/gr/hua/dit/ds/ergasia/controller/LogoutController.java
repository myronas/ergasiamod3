package gr.hua.dit.ds.ergasia.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LogoutController {

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        // Clear the token from the session
        session.removeAttribute("AuthToken");

        // Invalidate the session
        session.invalidate();

        // Redirect to the login page or any other public page
        return "redirect:/";
    }
}
