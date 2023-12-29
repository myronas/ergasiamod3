package gr.hua.dit.ds.ergasia.controller;

import gr.hua.dit.ds.ergasia.exception.UsernameAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import gr.hua.dit.ds.ergasia.entity.User;
import gr.hua.dit.ds.ergasia.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<?> createAdminUser(@RequestBody User user, @RequestParam("role") String role) {
        try {
            userService.createAdminUser(user, role);
            return ResponseEntity.ok().body("User created successfully.");
        } catch (UsernameAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Integer userId) {
        try {
            User user = userService.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching user: " + e.getMessage());
        }
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
        try {
            userService.softDeleteUser(userId);
            return ResponseEntity.ok().body("User deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting user: " + e.getMessage());
        }
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable("userId") Integer userId, @RequestBody User user) {
        try {
            userService.updateUser(userId, user);
            return ResponseEntity.ok().body("User updated successfully.");
        } catch (UsernameAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating user: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user: " + e.getMessage());
        }
    }

}
