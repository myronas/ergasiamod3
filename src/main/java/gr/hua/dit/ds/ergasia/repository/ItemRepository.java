package gr.hua.dit.ds.ergasia.repository;

import gr.hua.dit.ds.ergasia.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository  extends JpaRepository<Item, Integer> {
    boolean existsByName(String name);
}
