//package gr.hua.dit.ds.ergasia.handlers;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.net.URLEncoder;
//import java.util.Set;
//
//@Component
//public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//
//    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
//
//        logger.info("Roles: " + roles);
//        try {
//            if (roles.contains("ROLE_PLAYER")) {
//                logger.info("Redirecting to /main for PLAYER");
//                response.sendRedirect("/main");
//            } else if (roles.contains("ROLE_ADMIN")) {
//                logger.info("Redirecting to /admin/main for ADMIN");
//                response.sendRedirect("/admin/main");
//            } else {
//                logger.info("Redirecting to / for other roles");
//                response.sendRedirect("/");
//            }
//        } catch (Exception e){
//            logger.error("Error during redirection", e);
//            response.sendRedirect("/errorPage?message=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
//        }
//
//        super.onAuthenticationSuccess(request, response, authentication);
//        request.getSession().setAttribute("successMessage", "You have successfully logged in!");
//    }
//
//}
//
