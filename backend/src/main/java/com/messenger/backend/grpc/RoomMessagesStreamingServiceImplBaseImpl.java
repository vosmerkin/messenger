package com.messenger.backend.grpc;

import grpc_generated.RoomMessagesResponse;
import grpc_generated.RoomMessagesStreamingServiceGrpc;
import grpc_generated.RoomRequest;
import io.grpc.stub.StreamObserver;

public class RoomMessagesStreamingServiceImplBaseImpl extends RoomMessagesStreamingServiceGrpc.RoomMessagesStreamingServiceImplBase {
    @Override
    public void messageStreaming(RoomRequest request, StreamObserver<RoomMessagesResponse> responseObserver) {
        super.messageStreaming(request, responseObserver);
    }
}
