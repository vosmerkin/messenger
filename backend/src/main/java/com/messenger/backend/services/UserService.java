package com.messenger.backend.services;

import com.messenger.backend.entity.UserEntity;
import com.messenger.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity getByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public Boolean getUserStatus(Integer id) {
        UserEntity user = userRepository.findAllById(id);
        boolean status = user.getActiveStatus();
        return status;
    }

    public UserEntity createUser(String userName) {
        UserEntity newUser = new UserEntity(null, userName, new Date(), true, Collections.emptySet());
        return userRepository.save(newUser);
    }

    public UserEntity updateContactList(UserEntity userEntity) {
        Integer id = userEntity.getId();
        UserEntity result = UserEntity.EMPTY_ENTITY;
        if (userRepository.existsById(id)) {
            UserEntity existingUser = userRepository.getReferenceById(id);
            existingUser.setContactList(userEntity.getContactList());
            result = userRepository.save(existingUser);
        }
        return result;
    }
}
