package org.example.user.business.impl;

import jakarta.transaction.Transactional;
import org.example.user.business.interfaces.UserServiceInterface;
import org.example.user.domain.User;
import org.example.user.repository.UserRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Service
public class UserService implements UserServiceInterface{
    private final UserRepository userRepository;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public UserService(UserRepository userRepository, RabbitTemplate rabbitTemplate) {
        this.userRepository = userRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    public User createUserOrUpdate(Map<String, Object> userData) {
        String userId = (String) userData.get("sub");
        User user = userRepository.findById(userId).orElse(new User());
        user.setUserId(userId);
        user.setFirstName((String) userData.get("name"));  // Ensure these keys match the incoming data structure
        user.setEmail((String) userData.get("email"));
        user.setNickname((String) userData.get("nickname"));

        userRepository.save(user);

        sendUserCreatedMessage(user);
        return user;
    }

    private void sendUserCreatedMessage(User user) {
        rabbitTemplate.convertAndSend("user_exchange", "user.created", user.getUserId());
    }


    public void initiateUserDeletion(String userId) {
        // Send a message to start the cleanup process in other services
        rabbitTemplate.convertAndSend("user_exchange", "user.cleanup", userId);
    }

    @Override
    public boolean deleteByUsername(String username) {
        return false;
    }
}


//@Service
//public class UserService implements UserServiceInterface {
//    private final UserRepository userRepository;
//
//    @Autowired
//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//    @Transactional
//    public User createUserOrUpdate(@RequestBody Map<String, Object> userData) {
//        String userId;
//        try {
//            userId = ((String) userData.get("sub"));
//        } catch (NumberFormatException e) {
//            // Handle the case where the user ID is not a valid long
//            // Log the error and perhaps throw an application-specific exception or return null
//            return null;
//        }
//
//        User user = userRepository.findById(userId)
//                .orElse(new User());  // Create new or find existing
//
//
//
//
//
//
//        user.setUserId((String) userData.get("sub"));
//        user.setFirstName((String) userData.get("given_name"));
//        user.setEmail((String) userData.get("email"));
//        user.setNickname((String) userData.get("nickname"));
//
//        return userRepository.save(user);  // Save or update the user info
//    }
//}
