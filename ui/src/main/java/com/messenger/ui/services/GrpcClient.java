package com.messenger.ui.services;

import grpc_generated.RoomMessagesResponse;
import grpc_generated.RoomMessagesStreamingServiceGrpc;
import grpc_generated.RoomRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;

public class GrpcClient {
    private String address = Addresses.GRPC_ADDRESS;              //"localhost"
    private int port = Addresses.GRPC_PORT;                   //8080

    private final ManagedChannel channel;
    RoomMessagesStreamingServiceGrpc.RoomMessagesStreamingServiceBlockingStub stub;


    public GrpcClient() {
        channel = ManagedChannelBuilder
                .forAddress(address, port)
                .usePlaintext()
                .build();
        stub = RoomMessagesStreamingServiceGrpc.newBlockingStub(channel);
    }

    public Iterator<RoomMessagesResponse> sendRequest(int roomId, int userId) {
        return stub.messageStreaming(RoomRequest.newBuilder()
                .setUserId(userId)
                .setRoomId(roomId)
                .build());
    }

    public void shutdown() {
        channel.shutdown();
    }
}
