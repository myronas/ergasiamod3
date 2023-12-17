//package gr.hua.dit.ds.ergasia.service;
//
//import gr.hua.dit.ds.ergasia.entity.User;
//import gr.hua.dit.ds.ergasia.repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class UserRepositoryTests {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    private User testUser;
//
//    @BeforeEach
//    void setUp() {
//        testUser = new User();
//        testUser.setUsername("testUser");
//        testUser.setEmail("test@example.com");
//        testUser.setPassword("password"); // Normally you'd encode this
//        testUser.setLocation("athens");
//    }
//
//    @Test
//    void testCreateUser() {
//        User savedUser = userRepository.save(testUser);
//        assertNotNull(savedUser);
//        assertNotNull(savedUser.getId());
//    }
//
//    @Test
//    void testFindUserById() {
//        User savedUser = userRepository.save(testUser);
//        User foundUser = userRepository.findById(savedUser.getId()).orElse(null);
//        assertNotNull(foundUser);
//        assertEquals(savedUser.getUsername(), foundUser.getUsername());
//    }
//
//    @Test
//    void testUpdateUser() {
//        User savedUser = userRepository.save(testUser);
//        savedUser.setEmail("updated@example.com");
//        User updatedUser = userRepository.save(savedUser);
//
//        assertEquals("updated@example.com", updatedUser.getEmail());
//    }
//
//    @Test
//    void testDeleteUser() {
//        User savedUser = userRepository.save(testUser);
//        userRepository.deleteById(savedUser.getId());
//
//        assertFalse(userRepository.findById(savedUser.getId()).isPresent());
//    }
//}
