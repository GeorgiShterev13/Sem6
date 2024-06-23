package org.example.user.business.interfaces;

import org.example.user.domain.User;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;


import org.example.user.domain.User;
import java.util.Map;

public interface UserServiceInterface {
    User createUserOrUpdate(Map<String, Object> userData);
    void initiateUserDeletion(String userId);
    boolean deleteByUsername(String username); // Add this method
}
