package com.example.po_app.model;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String role;

    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String newPass) {
        password = newPass;
    }
    
    public void setUsername(String newUsername) {
        username = newUsername;
    }
}
