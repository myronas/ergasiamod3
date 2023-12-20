package gr.hua.dit.ds.ergasia.repository;

import gr.hua.dit.ds.ergasia.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

     Optional<User> findByUsername(String username);
     @Query("SELECT u FROM User u LEFT JOIN FETCH u.items")
     List<User> findAllWithItems();
     Optional<User> findById(Integer id);


}
