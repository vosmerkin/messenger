package com.messenger.common.dto;

import grpc_generated.MessageProto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

public class MessageDto implements Serializable {

//    private Integer id;


    public static final MessageDto EMPTY_ENTITY = new MessageDto(-1, null, "", RoomDto.EMPTY_ENTITY, UserDto.EMPTY_ENTITY);
    private Integer id;
    private Date messageDateTime;
    private String messageText;
    private RoomDto room;
    private UserDto user;


    public MessageDto() {
    }

    public MessageDto(Integer id, Date messageDateTime, String messageText, RoomDto room, UserDto user) {
        this.id = id;
        this.messageDateTime = messageDateTime;
        this.messageText = messageText;
        this.room = room;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getMessageDateTime() {
        return messageDateTime;
    }

    public void setMessageDateTime(Date messageDateTime) {
        this.messageDateTime = messageDateTime;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public RoomDto getRoom() {
        return room;
    }

    public void setRoom(RoomDto room) {
        this.room = room;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageDto that = (MessageDto) o;
        return Objects.equals(id, that.id) && Objects.equals(messageDateTime, that.messageDateTime) && Objects.equals(messageText, that.messageText) && Objects.equals(room, that.room) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, messageDateTime, messageText, room, user);
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "id=" + id +
                ", messageDateTime=" + messageDateTime +
                ", messageText='" + messageText + '\'' +
                ", room=" + room.toString() +
                ", user=" + user.toString() +
                '}';
    }

    public static MessageDto fromProto(MessageProto messageProto){
//        public MessageDto(Integer id, Date messageDateTime, String messageText, RoomDto room, UserDto user)
        if (messageProto == null) return MessageDto.EMPTY_ENTITY;
        return new MessageDto(messageProto.getMessageId(),
                Date.from(Instant.ofEpochSecond(messageProto.getMessageDateTime().getSeconds(), messageProto.getMessageDateTime().getNanos())),
                messageProto.getMessage(),
                RoomDto.fromProto(messageProto.getRoomProto()),
                UserDto.fromProto(messageProto.getUserProto()));
    }

}
