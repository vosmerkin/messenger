package com.messenger.backend.services;

import com.messenger.backend.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageService {
    @Autowired
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
}
