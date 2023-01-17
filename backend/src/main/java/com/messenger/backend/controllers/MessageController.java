package com.messenger.backend.controllers;

import com.messenger.backend.entity.MessageEntity;
import com.messenger.backend.entity.UserEntity;
import com.messenger.backend.exception.MessegeNotSentException;
import com.messenger.backend.exception.UserCreateFailed;
import com.messenger.backend.exception.UserNotFoundException;
import com.messenger.backend.services.MessageService;
import com.messenger.backend.services.UserService;
import com.messenger.common.dto.MessageDto;
import com.messenger.common.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {
    private static final Logger log = LoggerFactory.getLogger(MessageController.class);

    private final MessageService messageService;
    private final ModelMapper modelMapper;

    @Autowired
    public MessageController(MessageService messageService, ModelMapper modelMapper) {
        this.messageService = messageService;
        this.modelMapper = modelMapper;
    }

    @PutMapping(value = "/sendMessage", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateUser(@RequestBody MessageDto messageDtoDto) {
        log.info("/sendMessage_{}", messageDtoDto);
        MessageEntity messageToSend = modelMapper.map(messageDtoDto, MessageEntity.class);
        MessageEntity message = messageService.sendMessage(messageToSend);

        if (message == MessageEntity.EMPTY_ENTITY) {
            throw new MessegeNotSentException("Message " + messageToSend.toString() + " cannot be sent");
        }

    }

}
