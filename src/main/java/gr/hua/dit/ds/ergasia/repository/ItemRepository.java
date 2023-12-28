package gr.hua.dit.ds.ergasia.repository;

import gr.hua.dit.ds.ergasia.entity.Item;
import gr.hua.dit.ds.ergasia.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository  extends JpaRepository<Item, Integer> {
    boolean existsByName(String name);
    List<Item> findAllByDeletedAtIsNull();
    List<Item> findByUserAndDeletedAtIsNull(User user);
    List<Item> findByUserIsNull();
    boolean existsByNameAndIdNot(String name, Integer id);

}
