//package gr.hua.dit.ds.ergasia.handlers;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.DisabledException;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
//
//    @Override
//    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//        String errorMessage;
//
//
//            errorMessage = "Invalid username or password.";
//
//
//        request.getSession().setAttribute("errorMessage", errorMessage);
//        getRedirectStrategy().sendRedirect(request, response, "/login?error");
//    }
//}