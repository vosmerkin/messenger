package com.messenger.backend.services;

import com.messenger.backend.entity.MessageEntity;
import com.messenger.backend.repository.MessageRepository;
import com.messenger.backend.repository.RoomRepository;
import com.messenger.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final GrpcMessagesStreamingService messagesService;

    @Autowired
    public MessageService(MessageRepository messageRepository, UserRepository userRepository, RoomRepository roomRepository, GrpcMessagesStreamingService messagesService) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.messagesService = messagesService;
    }

    public MessageEntity sendMessage(MessageEntity message) {
        MessageEntity result = MessageEntity.EMPTY_ENTITY;
        if (message != null) {
            if (userRepository.existsById(message.getUser().getId()) && roomRepository.existsById(message.getRoom().getId())) {
                message.setUser(userRepository.findAllById(message.getUser().getId()));
                message.setRoom(roomRepository.findAllById(message.getRoom().getId()));
                result = messageRepository.save(message);
            }
            //broadcast to all room users
            messagesService.BroadcastNewMessage(message);

        }
        return result;
    }

    public List<MessageEntity> getByRoomId(Integer id) {

        List<MessageEntity> result = Collections.emptyList();
        if (id != null)
            result = messageRepository.getByRoomId(id);
        return result;
    }
}
