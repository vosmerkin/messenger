package com.messenger.backend.controllers;

import com.messenger.backend.entity.MessageEntity;
import com.messenger.backend.exception.MessegeNotSentException;
import com.messenger.backend.services.MessageService;
import com.messenger.common.dto.MessageDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public void sendMessage(@RequestBody MessageDto messageDto) {
        log.info("/sendMessage_{}", messageDto);
        MessageEntity messageToSend = modelMapper.map(messageDto, MessageEntity.class);
        MessageEntity message = messageService.sendMessage(messageToSend);

        if (message == MessageEntity.EMPTY_ENTITY) {
            throw new MessegeNotSentException("Message " + messageToSend.toString() + " cannot be sent");
        }

    }


    @GetMapping("/getMessages")    //request userinfo for logged in user
    public List<MessageDto> getMessages(@RequestParam(value = "room") Integer id) {
        log.info("/getMessages?room={}", id);
        List<MessageDto> result = new ArrayList<>();
        List<MessageEntity> messages = messageService.getByRoomId(id);
        if (messages.size() > 0) {
            for (MessageEntity message : messages) {
                MessageDto messageDto = modelMapper.map(message, MessageDto.class);
                result.add(messageDto);
            }
        } else {
            result=Collections.emptyList();
        }

        return result;
        //    curl -XGET  \"http://localhost:8080/getMessages?room=1\" "
    }

}
