package com.messenger.backend.services;

import com.messenger.backend.entity.UserEntity;
import com.messenger.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Boolean status =false;
        UserEntity user = userRepository.findAllById(id);
        status = user.getActiveStatus();
        return status;
    }


    public UserEntity updateContactList(UserEntity userEntity) {
        Integer id = userEntity.getId();
        if (userRepository.existsById(id)) {
            UserEntity existingUser = userRepository.getReferenceById(id);
            existingUser.setContactList(userEntity.getContactList());
            return userRepository.save(existingUser);
        }
        return null;
    }
}
