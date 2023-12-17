package gr.hua.dit.ds.ergasia.controller;

import gr.hua.dit.ds.ergasia.entity.Item;
import gr.hua.dit.ds.ergasia.entity.User;
import gr.hua.dit.ds.ergasia.exception.DuplicateItemException;
import gr.hua.dit.ds.ergasia.service.ItemService;
import gr.hua.dit.ds.ergasia.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.security.Principal;
import java.util.List;

@Controller
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);


    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @GetMapping("/main")
    public String main(Model model, Principal principal) {
        try {
            if (principal != null) {
                String username = principal.getName();
                User user = userService.findByUsername(username);
                List<Item> items = user.getItems();

                // Check if the items list is null or empty
                if (items == null || items.isEmpty()) {
                    logger.info("No items found for user: " + username);
                    model.addAttribute("noItemsMessage", "You currently have no items.");
                } else {
                    model.addAttribute("items", items);
                }
            }
        } catch (Exception e) {
            logger.error("Error in main method", e);
            // You can add a model attribute to show an error message on the page
            model.addAttribute("errorMessage", "An error occurred while fetching your items.");
        }
        return "main";
    }
    @GetMapping("/admin/main")
    public String adminMain(Model model) {
        List<User> allUsers = userService.findAllUsersWithItems(); // Method to be implemented in the service
        model.addAttribute("users", allUsers);
        return "adminMain";
    }

    // Method to show the item creation form
    @GetMapping("/items/new")
    public String showNewItemForm(Model model) {
        model.addAttribute("item", new Item());
        return "itemForm"; // Replace with your actual view name
    }

    // Method to process the item creation form
    @PostMapping("/items")
    public String createItem(Item item, Principal principal, RedirectAttributes redirectAttributes) {
        try {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            item.setUser(user);
            itemService.saveItem(item);
            redirectAttributes.addFlashAttribute("successMessage", "Item created successfully.");
        } catch (DuplicateItemException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error creating item.");
        }
        return "redirect:/main";
    }

    // Method to show the item update form
    @GetMapping("/items/{id}/edit")
    public String showUpdateItemForm(@PathVariable Integer id, Model model) {
        Item item = itemService.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        model.addAttribute("item", item);
        return "itemEditForm"; // Replace with your actual view name
    }

    // Method to process the item update form
    @PostMapping("/items/{id}")
    public String updateItem(@PathVariable Integer id, Item updatedItem, Principal principal, RedirectAttributes redirectAttributes) {
        String username = principal.getName();
        Item existingItem = itemService.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (!existingItem.getUser().getUsername().equals(username)) {
            redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to update this item.");
            return "redirect:/main";
        }

        updatedItem.setUser(existingItem.getUser()); // Retain the original user
        itemService.updateItem(id, updatedItem);
        return "redirect:/main";
    }

    // Method to delete an item
    @GetMapping("/items/{id}/delete")
    public String deleteItem(@PathVariable Integer id, Principal principal, RedirectAttributes redirectAttributes) {
        Item item = itemService.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (!item.getUser().getUsername().equals(principal.getName())) {
            redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to delete this item.");
            return "redirect:/main";
        }

        itemService.deleteItem(id);
        return "redirect:/main";
    }
}


