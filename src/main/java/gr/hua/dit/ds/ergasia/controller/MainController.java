package gr.hua.dit.ds.ergasia.controller;

import gr.hua.dit.ds.ergasia.entity.Item;
import gr.hua.dit.ds.ergasia.entity.Role;
import gr.hua.dit.ds.ergasia.entity.User;
import gr.hua.dit.ds.ergasia.exception.DuplicateItemException;
import gr.hua.dit.ds.ergasia.service.ItemService;
import gr.hua.dit.ds.ergasia.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/main")
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;


    @PostMapping("/items")
    public ResponseEntity<ApiResponse> createItem(@RequestBody Item item, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "User is not authenticated."));
        }
        try {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            item.setUser(user);
            Item savedItem = itemService.saveItem(item);
            return ResponseEntity.ok(new ApiResponse(true, "Item created successfully.", savedItem));
        } catch (DuplicateItemException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error creating item: " + e.getMessage()));
        }
    }


    @PutMapping("/items/{id}")
    public ResponseEntity<ApiResponse> updateItem(@PathVariable Integer id, @RequestBody Item updatedItem, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "User is not authenticated."));
        }
        try {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            boolean isAdmin = user.getRole() == Role.ADMIN;

            itemService.updateItem(id, updatedItem, username, isAdmin);
            return ResponseEntity.ok(new ApiResponse(true, "Item updated successfully.", updatedItem));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error updating item: " + e.getMessage()));
        }
    }


    @DeleteMapping("/items/{id}")
    public ResponseEntity<ApiResponse> deleteItem(@PathVariable Integer id, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "User is not authenticated."));
        }
        try {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            boolean isAdmin = user.getRole() == Role.ADMIN;

            itemService.deleteItem(id, username, isAdmin);
            return ResponseEntity.ok(new ApiResponse(true, "Item deleted successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error deleting item: " + e.getMessage()));
        }
    }




    @PatchMapping("/items/hide/{id}")
    public ResponseEntity<ApiResponse>hideItem(@PathVariable Integer id, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false, "User is not authenticated."));
        }

        try {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            Item item = itemService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Item not found"));

            if (!item.getUser().getId().equals(user.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(false, "You are not authorized to hide this item."));
            }

            if (item.isDeleted()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, "Item is already deleted and cannot be hidden."));
            }

            itemService.hideItem(id);
            return ResponseEntity.ok(new ApiResponse(true, "Item hidden successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, "Error hiding item: " + e.getMessage()));
        }
    }



    @PatchMapping("/items/lift")
    public ResponseEntity<ApiResponse> liftItem(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false, "User is not authenticated."));
        }

        try {
            String username = principal.getName();
            itemService.liftHiddenItem(username); // Adjust this method to handle only user's items
            return ResponseEntity.ok(new ApiResponse(true, "Hidden items lifted successfully."));
        } catch (Exception e) {
            logger.error("Error lifting hidden items", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, "Error lifting hidden items: " + e.getMessage()));
        }
    }
    @GetMapping("/my-items")
    public ResponseEntity<Map<String, Object>> getMyItems(Principal principal) {
        Map<String, Object> response = new HashMap<>();
        if (principal == null) {
            response.put("success", false);
            response.put("message", "User is not authenticated.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        try {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            List<Item> items = itemService.findAllItemsByUser(user);

            response.put("success", true);
            response.put("message", "Items fetched successfully.");
            response.put("data", items);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error fetching items", e);
            response.put("success", false);
            response.put("message", "Error fetching items: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public class ApiResponse {
        private boolean success;
        private String message;
        private Object data;

        public ApiResponse(boolean success, String message, Object data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }
        public ApiResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
            this.data = null; // Default to null when not provided
        }

        // Getters and Setters
        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }

}
