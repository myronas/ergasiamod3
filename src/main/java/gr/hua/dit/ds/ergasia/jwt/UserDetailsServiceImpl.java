//package gr.hua.dit.ds.ergasia.jwt;
//
//import gr.hua.dit.ds.ergasia.entity.User;
//import gr.hua.dit.ds.ergasia.jwt.UserDetailsImpl;
//import gr.hua.dit.ds.ergasia.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//
//        // Check if the user has been soft deleted
//        if (user.getDeletedAt() != null) {
//            throw new UsernameNotFoundException("This account has been deleted.");
//        }
//
//        return new UserDetailsImpl(user);
//    }
//}
