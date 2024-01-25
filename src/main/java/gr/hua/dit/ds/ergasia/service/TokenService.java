package gr.hua.dit.ds.ergasia.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private static final String AUTH_TOKEN_KEY = "AuthToken";


    // Store token in the session
    public void storeToken(HttpSession session, String token) {
        session.setAttribute(AUTH_TOKEN_KEY, token);
    }

    // Retrieve the token from the session
    public String getToken(HttpSession session) {
        return (String) session.getAttribute(AUTH_TOKEN_KEY);
    }

    // Add token to headers for outgoing requests
    public void addTokenToHeader(HttpHeaders headers, HttpSession session) {
        String token = getToken(session);
        if (token != null) {
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer"+ token);
        }
    }

    // Method to clear the token from the session
    public void clearToken(HttpSession session) {
        session.removeAttribute(AUTH_TOKEN_KEY);
    }
}
