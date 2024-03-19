package server.model;

import java.io.Serializable;

public class User implements Serializable {
    private String email;
    private String username;

    public User(String email, String username) {
        this.email = email;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

}
