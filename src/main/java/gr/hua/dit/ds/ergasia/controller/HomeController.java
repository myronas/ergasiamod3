package gr.hua.dit.ds.ergasia.controller;

//import gr.hua.dit.ds.ergasia.entity.Item;
//import gr.hua.dit.ds.ergasia.entity.User;
//import gr.hua.dit.ds.ergasia.repository.ItemRepository;
//import gr.hua.dit.ds.ergasia.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class HomeController {

    @GetMapping("/api/")
    public String home() {

        return "index";
    }
    @GetMapping("/about")
    public String about() {
        return "about";
    }
//    @Component
//    public class TestDataInitializer {
//
//        @Autowired
//        private UserRepository userRepository;
//
//        @Autowired
//        private ItemRepository itemRepository;
//
//        @PostConstruct
//        public void init() {
//            // Find a user (player) to assign the item to
//            Optional<User> userOptional = userRepository.findByUsername("player4");
//            if (userOptional.isPresent()) {
//                User user = userOptional.get();
//
//                // Create a new item
//                Item item = new Item();
//                item.setName("Test Item");
//                item.setCreatedAt(LocalDateTime.now());
//                item.setUpdatedAt(LocalDateTime.now());
//                item.setUser(user);
//                item.setId(1);
//
//
//                // Save the item
//                itemRepository.save(item);
//
//                System.out.println("Test item created and assigned to user: " + user.getUsername());
//            } else {
//                System.out.println("User not found");
//            }
//        }
//    }

}
