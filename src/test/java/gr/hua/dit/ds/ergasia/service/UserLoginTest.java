//package gr.hua.dit.ds.ergasia.service;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class UserLoginTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void testSuccessfulLogin() throws Exception {
//        // You should create a user in your database for this test, or use an existing user.
//        mockMvc.perform(MockMvcRequestBuilders.post("/login")
//                        .param("username", "existingUser")
//                        .param("password", "correctPassword"))
//                .andExpect(status().is3xxRedirection()); // Adjust based on your successful login flow
//    }
//
//    @Test
//    public void testUnsuccessfulLogin() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/login")
//                        .param("username", "nonExistingUser")
//                        .param("password", "wrongPassword"))
//                .andExpect(status().is4xxClientError()); // Assuming that failed login returns a 4xx status
//    }
//}
