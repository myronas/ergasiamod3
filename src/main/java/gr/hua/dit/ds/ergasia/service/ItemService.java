package gr.hua.dit.ds.ergasia.service;

import gr.hua.dit.ds.ergasia.entity.Item;
import gr.hua.dit.ds.ergasia.exception.DuplicateItemException;
import gr.hua.dit.ds.ergasia.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;


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

    public Item updateItem(Integer id, Item updatedItem) {
        return itemRepository.findById(id)
                .map(item -> {
                    item.setName(updatedItem.getName());
                    // set other properties if necessary
                    return itemRepository.save(item);
                })
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));
    }

    public void deleteItem(Integer id) {
        itemRepository.deleteById(id);
    }
}

