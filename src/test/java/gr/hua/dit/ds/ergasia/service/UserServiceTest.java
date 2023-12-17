//package gr.hua.dit.ds.ergasia.service;
//
//import gr.hua.dit.ds.ergasia.entity.User;
//import gr.hua.dit.ds.ergasia.repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Optional;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//class UserServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private UserService userService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void getUserByIdTest() {
//        User mockUser = new User(1, "testUser", "test@example.com", "TestLocation","testpassword");
//        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));
//
//        User result = userService.getUserById(1);
//
//        assertNotNull(result);
//        assertEquals("testUser", result.getUsername());
//        // Add more assertions as necessary
//    }
//
//    // Other tests...
//}
