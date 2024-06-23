package org.example.user.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "user_id")
    private String userId;  // Use Auth0 'sub' as the primary key
//    @Column(name = "auth0_id")  // Ensure this column is mapped correctly
//    private String auth0Id;
    @Column(name = "name")
    private String firstName;

    @Column(name = "email")
    private String email;
    @Column(name = "nickname")
    private String nickname;


    // Standard getters and setters
}
