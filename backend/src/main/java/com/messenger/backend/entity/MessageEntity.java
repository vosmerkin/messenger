package com.messenger.backend.entity;

import com.google.protobuf.Timestamp;
import com.messenger.common.dto.RoomDto;
import com.messenger.common.dto.UserDto;
import grpc_generated.MessageProto;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tbl_msg")
public class MessageEntity {
    public static final MessageEntity EMPTY_ENTITY = new MessageEntity(-1, null, "", RoomEntity.EMPTY_ENTITY, UserEntity.EMPTY_ENTITY);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_time")
    private Date messageDateTime;

    @Column(name = "msg_text")
    private String messageText;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id", insertable = true, updatable = true)
    private RoomEntity room;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", insertable = true, updatable = true)
    private UserEntity user;

    public MessageEntity() {
    }

    public MessageEntity(Integer id, Date messageDateTime, String messageText, RoomEntity room, UserEntity user) {
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

    public RoomEntity getRoom() {
        return room;
    }

    public void setRoom(RoomEntity room) {
        this.room = room;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageEntity message = (MessageEntity) o;
        return Objects.equals(id, message.id) && Objects.equals(messageDateTime, message.messageDateTime) && Objects.equals(messageText, message.messageText) && Objects.equals(room, message.room) && Objects.equals(user, message.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, messageDateTime, messageText, room, user);
    }

    @Override
    public String toString() {
        return "MessageEntity{" +
                "id=" + id +
                ", messageDateTime=" + messageDateTime +
                ", messageText='" + messageText + '\'' +
                ", room=" + room.getRoomName() +
                ", user=" + user.getUserName() +
                '}';
    }
    public static MessageProto toProto(MessageEntity message) {
        if (message==null) return null;
//        int32 message_id = 1;
//        google.protobuf.Timestamp message_date_time = 2;
//        string message = 3;
//        RoomProto room_proto = 4;
//        UserProto user_proto = 5;
//        int32 user_id = 6;
//        string user_name = 7;
        return MessageProto.newBuilder()
                .setMessageId(message.getId())
                .setMessageDateTime(Timestamp.newBuilder()
                        .setSeconds(message.getMessageDateTime().toInstant().getEpochSecond())
                        .setNanos(message.getMessageDateTime().toInstant().getNano())
                        .build())
                .setMessage(message.getMessageText())
                .setRoomProto(RoomEntity.toProto(message.getRoom()))
                .setUserProto(UserEntity.toProto(message.getUser()))
                .setUserId(message.getUser().getId())
                .setUserName(message.getUser().getUserName())
                .build();
    }
}
