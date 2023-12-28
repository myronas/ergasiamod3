package gr.hua.dit.ds.ergasia.controller;

import gr.hua.dit.ds.ergasia.entity.Item;
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
import java.util.List;

@RestController
@RequestMapping("/api/main")
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

//    @GetMapping("/main")
//    public ResponseEntity<?> getMainPage(Principal principal) {
//        if (principal == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated.");
//        }
//        try {
//            String username = principal.getName();
//            User user = userService.findByUsername(username);
//            List<Item> items = itemService.findAllItemsByUser(user);
//
//            if (items == null || items.isEmpty()) {
//                return ResponseEntity.ok("You currently have no items.");
//            } else {
//                return ResponseEntity.ok(items);
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching items.");
//        }
//    }@GetMapping("/items")
//public ResponseEntity<?> getMyItems(Principal principal) {
//    if (principal == null) {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated.");
//    }
//
//    try {
//        User user = userService.findByUsername(principal.getName());
//        List<Item> items = itemService.findAllItemsByUser(user);
//        return ResponseEntity.ok(items);
//    } catch (Exception e) {
//        logger.error("Error fetching items", e);
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching items.");
//    }
//}

    @PostMapping("/items")
    public ResponseEntity<?> createItem(@RequestBody Item item, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated.");
        }
        try {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            item.setUser(user);
            itemService.saveItem(item);
            return ResponseEntity.ok("Item created successfully.");
        } catch (DuplicateItemException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating item.");
        }
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<?> updateItem(@PathVariable Integer id, @RequestBody Item updatedItem, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated.");
        }

        try {
            Item existingItem = itemService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Item not found"));

            // Check if the item belongs to the authenticated user
            String username = principal.getName();
            if (!existingItem.getUser().getUsername().equals(username)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to update this item.");
            }
            if (existingItem.isDeleted()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Item is already deleted and cannot be updated.");
            }

            // Attempt to update the item, which will check for duplicate names
            itemService.updateItem(id, updatedItem);
            return ResponseEntity.ok("Item updated successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating item: " + e.getMessage());
        }
    }



    @DeleteMapping("/items/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Integer id, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated.");
        }

        try {
            Item item = itemService.findById(id).orElse(null);

            // Check if the item exists
            if (item == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found.");
            }
            if(item.isDeleted()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Item is already deleted.");
            }

            String username = principal.getName();
            if (!item.getUser().getUsername().equals(username)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to delete this item.");
            }

            itemService.deleteItem(id);
            return ResponseEntity.ok("Item deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting item: " + e.getMessage());
        }
    }
//    @GetMapping("/items")
//    public ResponseEntity<List<Item>> getMyItems(Principal principal) {
//        if (principal == null) {
//            // If you want to return a string message in ResponseEntity of type List<Item>, you cannot directly return a string.
//            // You need to return an empty list with the error status or change the method return type to ResponseEntity<?>.
//            // For this example, let's return an empty list with the error status:
//            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.UNAUTHORIZED);
//        }
//
//        try {
//            User user = userService.findByUsername(principal.getName());
//            List<Item> items = itemService.findAllItemsByUser(user);
//
//            if (items.isEmpty()) {
//                // Return an empty list with HTTP status 204 - No Content
//                return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NO_CONTENT);
//            } else {
//                // Return the list of items with HTTP status 200 - OK
//                return new ResponseEntity<>(items, HttpStatus.OK);
//            }
//        } catch (Exception e) {
//            // Log the error for internal tracking
//            logger.error("Error fetching items", e);
//
//            // Return an empty list with HTTP status 500 - Internal Server Error
//            // You could also return a custom error object if you have one
//            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }



    @PatchMapping("/items/hide/{id}")
    public ResponseEntity<?> hideItem(@PathVariable Integer id, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated.");
        }

        try {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            Item item = itemService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Item not found"));

            // Check if the item belongs to the user
            if (!item.getUser().getId().equals(user.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to hide this item.");
            }

            // Check if the item is already deleted
            if (item.isDeleted()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Item is already deleted and cannot be hidden.");
            }

            // Hide the item since it's not deleted
            itemService.hideItem(id);
            return ResponseEntity.ok("Item hidden successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error hiding item: " + e.getMessage());
        }
    }


    @PatchMapping("/items/lift")
    public ResponseEntity<?> liftItem(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated.");
        }

        try {
            String username = principal.getName();
            itemService.liftHiddenItem(username); // Adjust this method to handle only user's items
            return ResponseEntity.ok("Hidden items lifted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error lifting hidden items: " + e.getMessage());
        }
    }
}
