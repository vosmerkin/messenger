package com.messenger.backend.services;

import com.messenger.backend.entity.MessageEntity;
import com.messenger.backend.entity.RoomEntity;
import com.messenger.backend.repository.MessageRepository;
import com.messenger.backend.repository.RoomRepository;
import com.messenger.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    private final MessageRepository messageRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RoomRepository roomRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository, RoomRepository roomRepository) {
        this.messageRepository = messageRepository;
        this.userRepository=userRepository;
        this.roomRepository=roomRepository;
    }

    public MessageEntity sendMessage(MessageEntity message) {
        MessageEntity result = MessageEntity.EMPTY_ENTITY;
        if (message != null) {
            if (userRepository.existsById(message.getUser().getId()) && roomRepository.existsById(message.getRoom().getId())) {
                result=messageRepository.save(message);
            }
        }
        return result;
    }
}
