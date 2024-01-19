//package gr.hua.dit.ds.ergasia.controller;
//
//import gr.hua.dit.ds.ergasia.dto.ItemDTO;
//import gr.hua.dit.ds.ergasia.dto.UserDTO;
//import gr.hua.dit.ds.ergasia.entity.Item;
//import gr.hua.dit.ds.ergasia.exception.UsernameAlreadyExistsException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import gr.hua.dit.ds.ergasia.entity.User;
//import gr.hua.dit.ds.ergasia.service.UserService;
//import java.util.stream.Collectors;
//
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/admin")
//public class AdminController {
//    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
//
//    @Autowired
//    private UserService userService;
//
//    @PostMapping("/users")
//    public ResponseEntity<ApiResponse> createAdminUser(@RequestBody User user, @RequestParam("role") String role) {
//        try {
//            userService.createAdminUser(user, role);
//            return ResponseEntity.ok(new ApiResponse(true, "User created successfully."));
//        } catch (UsernameAlreadyExistsException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, e.getMessage()));
//        } catch (Exception e) {
//            logger.error("Error creating admin user", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, "Error creating user: " + e.getMessage()));
//        }
//    }
//
//    @GetMapping("/users/{userId}")
//    public ResponseEntity<ApiResponse> getUser(@PathVariable Integer userId) {
//        try {
//            User user = userService.findById(userId)
//                    .orElseThrow(() -> new RuntimeException("User not found"));
//
//            if (user.isDeleted()) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                        .body(new ApiResponse(false, "User is deleted."));
//            }
//
//            UserDTO userDTO = userService.convertToDTO(user);
//            ApiResponse response = new ApiResponse(true, "User fetched successfully.", userDTO);
//
//            // Log the response to see what's being returned
//            logger.info("User response: {}", response);
//
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            logger.error("Error fetching user", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ApiResponse(false, "Error fetching user: " + e.getMessage()));
//        }
//    }
//
//
//    @DeleteMapping("/users/{userId}")
//    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) {
//        try {
//            userService.softDeleteUser(userId);
//            return ResponseEntity.ok(new ApiResponse(true, "User deleted successfully."));
//        } catch (Exception e) {
//            logger.error("Error deleting user", e);
//            return ResponseEntity.badRequest().body(new ApiResponse(false, "Error deleting user: " + e.getMessage()));
//        }
//    }
//
//    @PutMapping("/users/{userId}")
//    public ResponseEntity<ApiResponse> updateUser(@PathVariable("userId") Integer userId, @RequestBody User user) {
//        try {
//            userService.updateUser(userId, user);
//            return ResponseEntity.ok(new ApiResponse(true, "User updated successfully."));
//        } catch (UsernameAlreadyExistsException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, "Error updating user: " + e.getMessage()));
//        } catch (Exception e) {
//            logger.error("Error updating user", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, "Error updating user: " + e.getMessage()));
//        }
//    }
//     class ApiResponse {
//        private boolean success;
//        private String message;
//        private Object data;
//
//        public ApiResponse(boolean success, String message) {
//            this.success = success;
//            this.message = message;
//        }
//        public ApiResponse(boolean success, String message, Object data ){
//            this.success = success;
//            this.message = message;
//            this.data = data;
//        }
//
//
//        public boolean isSuccess() {
//            return success;
//        }
//
//        public void setSuccess(boolean success) {
//            this.success = success;
//        }
//
//        public String getMessage() {
//            return message;
//        }
//
//        public void setMessage(String message) {
//            this.message = message;
//        }
//         @Override
//         public String toString() {
//             return "ApiResponse{" +
//                     "success=" + success +
//                     ", message='" + message + '\'' +
//                     ", data=" + data +
//                     '}';
//         }
//    }
//
//
//}
