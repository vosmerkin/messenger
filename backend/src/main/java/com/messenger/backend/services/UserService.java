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
        UserEntity result = userRepository.findByUserName(userName);
        if (result == null) return UserEntity.EMPTY_ENTITY;
        return result;
    }

    public UserEntity getByUserId(Integer id) {
        UserEntity result = userRepository.getReferenceById(id);
        if (result == null) return UserEntity.EMPTY_ENTITY;
        return result;
    }

//    public Boolean getUserStatus(Integer id) {
//        UserEntity user = userRepository.getReferenceById(id);
//        boolean status = user.getActiveStatus();
//        return status;
//    }

    public UserEntity createUser(String userName) {
        UserEntity newUser = UserEntity.EMPTY_ENTITY;
        UserEntity existingUser = UserEntity.EMPTY_ENTITY;
        if (userName != null && !userName.isEmpty())
            existingUser = userRepository.findByUserName(userName);
        if (existingUser == null)
            newUser = userRepository.save(new UserEntity(null, userName, new Date(), true, Collections.emptySet()));
        return newUser;
    }

    public UserEntity updateUser(UserEntity userEntity) {
        UserEntity result = UserEntity.EMPTY_ENTITY;
        if (userEntity != null) {
            Integer id = userEntity.getId();
            if (userRepository.existsById(id)) {
                UserEntity existingUser = userRepository.getReferenceById(id);
                existingUser.setUserName(userEntity.getUserName());
                existingUser.setActiveStatus(userEntity.getActiveStatus());
                existingUser.setContactList(userEntity.getContactList());
                result = userRepository.save(existingUser);
            }
        }
        return result;
    }


}
