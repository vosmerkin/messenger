package com.messenger.backend.services;

import com.messenger.backend.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class messageService {
    @Autowired
    private final MessageRepository messageRepository;

    public messageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
}
