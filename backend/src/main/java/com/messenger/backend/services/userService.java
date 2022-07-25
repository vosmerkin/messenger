package com.messenger.backend.services;

import com.messenger.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class userService {
    @Autowired
    private final UserRepository userRepository;

    public userService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
