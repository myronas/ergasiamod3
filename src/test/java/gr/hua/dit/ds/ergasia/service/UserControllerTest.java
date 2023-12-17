//package gr.hua.dit.ds.ergasia.controller;
//
//import gr.hua.dit.ds.ergasia.entity.User;
//import gr.hua.dit.ds.ergasia.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(UserController.class)
//class UserControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserService userService;
//
//    @Test
//    void getAllUsersTest() throws Exception {
//        User user1 = new User(1, "User1", "user1@example.com", "Location1","password");
//        User user2 = new User(2, "User2", "user2@example.com", "Location2","password2");
//        List<User> users = Arrays.asList(user1, user2);
//
//        when(userService.getAllUsers()).thenReturn(users);
//
//        mockMvc.perform(get("/users"))
//                .andExpect(status().isOk())
//                .andExpect(model().attribute("users", users))
//                .andExpect(view().name("users"));
//    }
//}
//
