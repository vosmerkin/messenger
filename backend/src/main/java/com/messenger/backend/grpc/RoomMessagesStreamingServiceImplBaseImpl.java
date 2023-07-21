package com.messenger.backend.grpc;

import grpc_generated.RoomMessagesResponse;
import grpc_generated.RoomMessagesStreamingServiceGrpc;
import grpc_generated.RoomRequest;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoomMessagesStreamingServiceImplBaseImpl extends RoomMessagesStreamingServiceGrpc.RoomMessagesStreamingServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(RoomMessagesStreamingServiceImplBaseImpl.class);


    @Override
    public void messageStreaming(RoomRequest request, StreamObserver<RoomMessagesResponse> responseObserver) {
//        super.messageStreaming(request, responseObserver);
        log.debug("Message streaming request received for room_id=" + request.toString());


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
