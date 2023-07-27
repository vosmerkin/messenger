package com.messenger.backend.grpc;

import com.google.protobuf.MapEntry;
import com.messenger.backend.entity.MessageEntity;
import com.messenger.backend.services.MessageService;
import grpc_generated.RoomMessagesResponse;
import grpc_generated.RoomMessagesStreamingServiceGrpc;
import grpc_generated.RoomRequest;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RoomMessagesStreamingServiceImplBaseImpl extends RoomMessagesStreamingServiceGrpc.RoomMessagesStreamingServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(RoomMessagesStreamingServiceImplBaseImpl.class);

    @Autowired
    private final MessageService messageService;
    private Map<ObserverKey, StreamObserver<RoomMessagesResponse>> streamObserverMap;

    public RoomMessagesStreamingServiceImplBaseImpl(MessageService messageService) {
        this.messageService = messageService;
    }

    public void BroadcastNewMessage(MessageEntity message) {
        if (message == null) return;
        for (Map.Entry<ObserverKey, StreamObserver<RoomMessagesResponse>> entry : streamObserverMap.entrySet()) {
            StreamObserver<RoomMessagesResponse> responseObserver = entry.getValue();
            if (message.getRoom().getId() == entry.getKey().getRoomId()) {
                RoomMessagesResponse response = RoomMessagesResponse.newBuilder()
                        .setUserId(message.getUser().getId())
                        .setUserName(message.getUser().getUserName())
                        .setMessageId(message.getId())
                        .setMessage(message.getMessageText())
                        .build();
                responseObserver.onNext(response);
            }
        }
    }


    @Override
    public void messageStreaming(RoomRequest request, StreamObserver<RoomMessagesResponse> responseObserver) {
//        super.messageStreaming(request, responseObserver);
        log.debug("Message streaming request from user_id=" + request.getUserId() + " for room_id=" + request.getRoomId());


        streamObserverMap.put(
                new ObserverKey(request.getUserId(), request.getRoomId()),
                responseObserver
        );


        //get previous messages and send to client
        //or we can get history messages with REST and use GRPC for message updating
        List<MessageEntity> roomMessages = messageService.getByRoomId(request.getRoomId());
        if (roomMessages != null && roomMessages.size() > 0) {
            for (MessageEntity message : roomMessages) {
                //            int32 user_id = 1;
                //            string user_name = 4;
                //            int32 message_id = 3;
                //            string message = 2;
                RoomMessagesResponse response = RoomMessagesResponse.newBuilder()
                        .setUserId(message.getUser().getId())
                        .setUserName(message.getUser().getUserName())
                        .setMessageId(message.getId())
                        .setMessage(message.getMessageText())
                        .build();
                responseObserver.onNext(response);
            }
        }

        //
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
