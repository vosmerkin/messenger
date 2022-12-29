package com.messenger.backend.services;

import com.messenger.backend.entity.RoomEntity;
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
        UserEntity result = userRepository.findAllById(id);
        if (result == null) return UserEntity.EMPTY_ENTITY;
        return result;
    }

    public Boolean getUserStatus(Integer id) {
        UserEntity user = userRepository.findAllById(id);
        boolean status = user.getActiveStatus();
        return status;
    }

    public UserEntity createUser(String userName) {
        UserEntity newUser = UserEntity.EMPTY_ENTITY;
        UserEntity existingUser = UserEntity.EMPTY_ENTITY;
        if (!userName.isEmpty())
            existingUser = userRepository.findByUserName(userName);
        if (existingUser == null)
            newUser = userRepository.save(new UserEntity(null, userName, new Date(), true, Collections.emptySet()));

        return newUser;
    }

    public UserEntity updateUserStatus(UserEntity userEntity) {
        Integer id = userEntity.getId();
        UserEntity result = UserEntity.EMPTY_ENTITY;
        if (userRepository.existsById(id)) {
            UserEntity existingUser = userRepository.getReferenceById(id);
            existingUser.setActiveStatus(userEntity.getActiveStatus());
            result = userRepository.save(existingUser);
        }
        return result;
    }


}
