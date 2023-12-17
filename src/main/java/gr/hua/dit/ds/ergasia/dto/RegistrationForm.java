package gr.hua.dit.ds.ergasia.dto;

public class RegistrationForm {
    private String username;
    private String email;
    private String location;
    private String password;

    public RegistrationForm(String username, String email, String location, String password) {
        this.username = username;
        this.email = email;
        this.location = location;
        this.password = password;
    }

    public RegistrationForm() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
