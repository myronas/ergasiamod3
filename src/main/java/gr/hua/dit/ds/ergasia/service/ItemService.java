package gr.hua.dit.ds.ergasia.service;

import gr.hua.dit.ds.ergasia.entity.Item;
import gr.hua.dit.ds.ergasia.entity.User;
import gr.hua.dit.ds.ergasia.exception.DuplicateItemException;
import gr.hua.dit.ds.ergasia.repository.ItemRepository;
import gr.hua.dit.ds.ergasia.repository.UserRepository;
import jakarta.persistence.Id;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ItemService {
    private  static final Logger logger = LoggerFactory.getLogger(ItemService.class);
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Item> findAllItemsByUser(User user) {
        return itemRepository.findByUserAndDeletedAtIsNull(user);
    }



    public List<Item> findAllItems() {
        return itemRepository.findAll();
    }
    public Optional<Item> findById(Integer id) {
        return itemRepository.findById(id);
    }


    public Item saveItem(Item item) throws DuplicateItemException {
        if (itemRepository.existsByName(item.getName())) {
            throw new DuplicateItemException("An item with the name " + item.getName() + " already exists.");
        }
        if (item != null && item.getName() != null && !item.getName().isEmpty()) {

            return itemRepository.save(item);
        } else {
            throw new IllegalArgumentException("Item details are not complete.");
        }
    }

    public boolean updateItem(Integer id, Item updatedItem, String username, boolean isAdmin) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (!isAdmin && !item.getUser().getUsername().equals(username)) {
            throw new RuntimeException("You are not authorized to update this item.");
        }

        if (item.isDeleted()) {
            throw new RuntimeException("Item is already deleted and cannot be updated.");
        }

        boolean nameExists = itemRepository.existsByNameAndIdNot(updatedItem.getName(), id);
        if (nameExists) {
            throw new RuntimeException("An item with this name already exists.");
        }

        item.setName(updatedItem.getName());
        item.setUpdatedAt(LocalDateTime.now());
        itemRepository.save(item);
        return true;
    }

    public void deleteItem(Integer id, String username, boolean isAdmin) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));

        if (!isAdmin && !item.getUser().getUsername().equals(username)) {
            throw new RuntimeException("You are not authorized to delete this item.");
        }

        if (item.isDeleted()) {
            throw new RuntimeException("Item is already deleted.");
        }

        item.setDeletedAt(LocalDateTime.now());
        itemRepository.save(item);
    }
    public void hideItem(Integer itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + itemId));
        item.setUser(null); // Setting user to null to represent that it's hidden
        itemRepository.save(item);
    }
    @Transactional
    public void liftHiddenItem(String username) {
        // Retrieve a list of hidden items (items with null user)
        List<Item> hiddenItems = itemRepository.findByUserIsNull();
        if (hiddenItems.isEmpty()) {
            throw new RuntimeException("No hidden items available to lift.");
        }

        // Randomly select one of the hidden items
        Random random = new Random();
        int randomIndex = random.nextInt(hiddenItems.size());
        Item selectedItem = hiddenItems.get(randomIndex);

        // Assign the selected item to the user
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        selectedItem.setUser(user);

        itemRepository.save(selectedItem);
    }



    public List<Item> findAllItemsForAdmin() {
        return itemRepository.findAll();
    }




}

