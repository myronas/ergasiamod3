package gr.hua.dit.ds.ergasia.service;

import gr.hua.dit.ds.ergasia.entity.Item;
import gr.hua.dit.ds.ergasia.entity.Role;
import gr.hua.dit.ds.ergasia.entity.User;
import gr.hua.dit.ds.ergasia.exception.UsernameAlreadyExistsException;
import gr.hua.dit.ds.ergasia.repository.ItemRepository;
import gr.hua.dit.ds.ergasia.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    public void registerNewUserAccount(User user) throws UsernameAlreadyExistsException {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("There is already an account with that username: " + user.getUsername());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.PLAYER); // Default role
        userRepository.save(user);
    }

    @Transactional
    public void ensureAdminUser() {
        String adminUsername = "admin2";
        if (userRepository.findByUsername(adminUsername).isEmpty()) {
            User admin = new User();
            admin.setUsername(adminUsername);
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRole(Role.ADMIN);
            admin.setEmail("admin@gmail.com");
            userRepository.save(admin);
        }
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public List<User> findAllUsersWithItems() {
        return userRepository.findAllWithItems();

    }

    @Transactional
    public void createAdminUser(User user, String roleName) throws UsernameAlreadyExistsException {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("There is already an account with that username: " + user.getUsername());
        }//createAdminUser means that an admin creates a new user
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.valueOf(roleName));
        user.setCreatedAt(LocalDateTime.now());
        user.setDeletedAt(null);

        userRepository.save(user);
    }

    @Transactional
    public void softDeleteUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        if (user.getDeletedAt() != null) {
            throw new RuntimeException("User is already deleted.");
        }

        if (user.getItems() != null) {
            for (Item item : user.getItems()) {
                // Assuming 'userId' field in Item entity links it to the User
                item.setId(0); // Set to 0 or appropriate orphaned state
                itemRepository.save(item);
            }
        }

        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
    }


    @Transactional
    public void updateUser(Integer userId, User updatedUser) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.isDeleted()) {
            throw new RuntimeException("This account is deleted.");
        }

        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setRole(updatedUser.getRole());

        userRepository.save(user);
    }
}


