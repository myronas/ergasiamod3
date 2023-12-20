package gr.hua.dit.ds.ergasia.service;

import gr.hua.dit.ds.ergasia.entity.Item;
import gr.hua.dit.ds.ergasia.entity.User;
import gr.hua.dit.ds.ergasia.exception.DuplicateItemException;
import gr.hua.dit.ds.ergasia.repository.ItemRepository;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private  static final Logger logger = LoggerFactory.getLogger(ItemService.class);
    @Autowired
    private ItemRepository itemRepository;

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

    public void updateItem(Integer id, Item updatedItem){
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        item.setName(updatedItem.getName());
        // Update other fields as necessary
        item.setUpdatedAt(LocalDateTime.now()); // Update the 'updatedAt' timestamp
        itemRepository.save(item);
    }

    public  void deleteItem(Integer id) {
        logger.debug("Fetching item for soft delete with id: " + id);
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));

        logger.debug("Item before soft delete: " + item);
        item.setDeletedAt(LocalDateTime.now());
        itemRepository.save(item);
        logger.debug("Item after soft delete: " + item);
    }
    public List<Item> findAllItemsForAdmin() {
        return itemRepository.findAll();
    }




}

