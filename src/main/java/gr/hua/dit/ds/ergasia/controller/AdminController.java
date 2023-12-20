package gr.hua.dit.ds.ergasia.controller;

import gr.hua.dit.ds.ergasia.exception.UsernameAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import gr.hua.dit.ds.ergasia.entity.User;
import gr.hua.dit.ds.ergasia.service.UserService;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/users/create")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new User());
        return "adminCreateUser";
    }

    @PostMapping("/users")
    public String createAdminUser(@ModelAttribute User user, @RequestParam("role") String role, RedirectAttributes redirectAttributes) {
        try {

            userService.createAdminUser(user, role);

            redirectAttributes.addFlashAttribute("successMessage", "User created successfully.");
            return "redirect:/admin/main";

        } catch (UsernameAlreadyExistsException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());

            return "adminCreateUser";
        }

    }
    @GetMapping
    public String adminDashboard(Model model) {
        // Add any model attributes needed for the dashboard
        return "adminMain";
    }

    @GetMapping("/users/delete/{userId}")
    public String deleteUser(@PathVariable Integer userId, RedirectAttributes redirectAttributes) {
        try {
            userService.softDeleteUser(userId);
            redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting user: " + e.getMessage());
        }
        return "redirect:/admin/main";
    }
    @GetMapping("/users/edit/{userId}")
    public String showEditUserForm(@PathVariable("userId") Integer userId, Model model) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("user", user);
        return "adminUpdateUser"; // Reuse the same view with a conditional check for edit mode
    }

    @PostMapping("/users/update/{userId}")
    public String updateUser(@PathVariable("userId") Integer userId, @ModelAttribute User user, @RequestParam("role") String role, RedirectAttributes redirectAttributes) {
        try {
            userService.updateUser(userId, user, role);
            redirectAttributes.addFlashAttribute("successMessage", "User updated successfully.");
            return "redirect:/admin/main";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating user: " + e.getMessage());
            return "redirect:/admin/users/edit/" + userId;
        }
    }
}

