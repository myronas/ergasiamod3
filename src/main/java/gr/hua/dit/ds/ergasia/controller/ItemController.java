//package gr.hua.dit.ds.ergasia.controller;
//
//import gr.hua.dit.ds.ergasia.dto.ItemDTO; // Assume this DTO exists in your project
//import gr.hua.dit.ds.ergasia.service.ItemService; // Service class to handle API calls
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import jakarta.servlet.http.HttpSession;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/items")
//public class ItemController {
//
//    @Autowired
//    private ItemService itemService;
//
//    // Display form to create a new item
//    @GetMapping("/new")
//    public String newItemForm(Model model) {
//        model.addAttribute("item", new ItemDTO());
//        return "itemForm"; // Thymeleaf template for item form
//    }
//
//    // Process creating a new item
//    @PostMapping
//    public String createItem(@ModelAttribute ItemDTO item, HttpSession session, Model model) {
//        String response = itemService.createItem(item, session);
//        model.addAttribute("message", response);
//        return "redirect:/items/my-items"; // Redirect to the user's items list
//    }
//
//    // Display form to update an item
//    @GetMapping("/{id}/edit")
//    public String editItemForm(@PathVariable Integer id, Model model) {
//        ItemDTO item = itemService.getItemById(id);
//        model.addAttribute("item", item);
//        return "itemForm"; // Reuse the item form for editing
//    }
//
//    // Process updating an item
//    @PostMapping("/{id}/update")
//    public String updateItem(@PathVariable Integer id, @ModelAttribute ItemDTO item, HttpSession session, Model model) {
//        String response = itemService.updateItem(id, item, session);
//        model.addAttribute("message", response);
//        return "redirect:/items/my-items";
//    }
//
//    // Process deleting an item
//    @GetMapping("/{id}/delete")
//    public String deleteItem(@PathVariable Integer id, HttpSession session, Model model) {
//        String response = itemService.deleteItem(id, session);
//        model.addAttribute("message", response);
//        return "redirect:/items/my-items";
//    }
//
//    // Process hiding an item
//    @GetMapping("/{id}/hide")
//    public String hideItem(@PathVariable Integer id, HttpSession session, Model model) {
//        String response = itemService.hideItem(id, session);
//        model.addAttribute("message", response);
//        return "redirect:/items/my-items";
//    }
//
//    // Process lifting hidden items
//    @GetMapping("/lift")
//    public String liftHiddenItems(HttpSession session, Model model) {
//        String response = itemService.liftHiddenItems(session);
//        model.addAttribute("message", response);
//        return "redirect:/items/my-items";
//    }
//
//    // Display user's items
//    @GetMapping("/my-items")
//    public String myItems(HttpSession session, Model model) {
//        List<ItemDTO> items = itemService.getMyItems(session);
//        model.addAttribute("items", items);
//        return "myItems"; // Thymeleaf template to display user's items
//    }
//}
//
