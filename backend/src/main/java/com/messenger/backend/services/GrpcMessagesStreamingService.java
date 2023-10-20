package com.messenger.backend.services;

import com.google.protobuf.Timestamp;
import com.messenger.backend.entity.MessageEntity;
import grpc_generated.RoomMessagesResponse;
import grpc_generated.RoomMessagesStreamingServiceGrpc;
import grpc_generated.RoomRequest;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@GRpcService
public class GrpcMessagesStreamingService extends RoomMessagesStreamingServiceGrpc.RoomMessagesStreamingServiceImplBase {
    private static final Logger LOG = LoggerFactory.getLogger(GrpcMessagesStreamingService.class);

    private final MessageService messageService;
    private final Map<ObserverKey, StreamObserver<RoomMessagesResponse>> streamObserverMap = new HashMap<>();

    @Autowired
    public GrpcMessagesStreamingService(@Lazy MessageService messageService) {
        this.messageService = messageService;
    }


    public void broadcastNewMessage(MessageEntity message) {
        if (message == null) return;
        LOG.info("Broadcadting new message for users in room_id = {}", message.getRoom().getId());
        for (Map.Entry<ObserverKey, StreamObserver<RoomMessagesResponse>> entry : streamObserverMap.entrySet()) {
            StreamObserver<RoomMessagesResponse> responseObserver = entry.getValue();
            if (message.getRoom().getId() == entry.getKey().getRoomId()) {
                LOG.info("Broadcadting new message {} from user {}", message,message.getUser());
                RoomMessagesResponse response = RoomMessagesResponse.newBuilder()
                        .setMessageId(message.getId())
                        .setMessageDateTime(Timestamp.newBuilder()
                                .setSeconds(message.getMessageDateTime().toInstant().getEpochSecond())
                                .setNanos(message.getMessageDateTime().toInstant().getNano())
                                .build())
                        .setMessage(message.getMessageText())
                        .setRoomId(message.getRoom().getId())
                        .setUserId(message.getUser().getId())
                        .setUserName(message.getUser().getUserName())
                        .setMessageProto(MessageEntity.toProto(message))
                        .build();
                LOG.info("Broadcadting response {}", response);

                responseObserver.onNext(response);
            }
        }
    }


    @Override
    public void messageStreaming(RoomRequest request, StreamObserver<RoomMessagesResponse> responseObserver) {
        LOG.info("Message streaming request from user_id=" + request.getUserId() + " for room_id=" + request.getRoomId());
        streamObserverMap.put(
                new ObserverKey(request.getUserId(), request.getRoomId()),
                responseObserver
        );
        //get previous messages and send to client
        //or we can get history messages with REST and use GRPC for message updating
        List<MessageEntity> roomMessages = messageService.getByRoomId(request.getRoomId());
        if (roomMessages != null ) {
            for (MessageEntity message : roomMessages) {
                responseObserver.onNext(message.toRoomMessagesResponse());
            }
        }
//        responseObserver.onCompleted();
    }

    public final class ObserverKey {
        private int userId;
        private int roomId;

        public ObserverKey(int userId, int roomId) {
            this.userId = userId;
            this.roomId = roomId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getRoomId() {
            return roomId;
        }

        public void setRoomId(int roomId) {
            this.roomId = roomId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ObserverKey that = (ObserverKey) o;

            if (userId != that.userId) return false;
            return roomId == that.roomId;
        }

        @Override
        public int hashCode() {
            int result = userId;
            result = 31 * result + roomId;
            return result;
        }
    }
}
