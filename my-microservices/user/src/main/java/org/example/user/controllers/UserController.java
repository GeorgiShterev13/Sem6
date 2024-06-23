package org.example.user.controllers;

import org.example.user.business.interfaces.UserServiceInterface;
import org.example.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceInterface userService;

    @PostMapping("/createOrUpdate")
    public ResponseEntity<User> createUserOrUpdate(@RequestBody Map<String, Object> userData) {
        try {
            User updatedUser = userService.createUserOrUpdate(userData);
            if (updatedUser == null) {
                return ResponseEntity.badRequest().body(null);
            }
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        try {
            userService.initiateUserDeletion(userId);
            return ResponseEntity.accepted().build();  // Acknowledge the request; completion is async
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
