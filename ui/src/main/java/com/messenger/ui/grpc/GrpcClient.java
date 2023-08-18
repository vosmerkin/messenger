package com.messenger.ui.grpc;

import com.messenger.common.dto.MessageDto;
import com.messenger.ui.form.StartForm;
import com.messenger.ui.services.PropertyManager;
import grpc_generated.RoomMessagesResponse;
import grpc_generated.RoomMessagesStreamingServiceGrpc;
import grpc_generated.RoomRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

public class GrpcClient {
    private static final Logger LOG = LoggerFactory.getLogger(GrpcClient.class);
    private final String grpcHost;
    private final ManagedChannel channel;
    private final RoomMessagesStreamingServiceGrpc.RoomMessagesStreamingServiceBlockingStub stub;
    private final StartForm form;


    public GrpcClient(StartForm form) {
        this.form = form;
        grpcHost = PropertyManager.getProperty("grpc.host");
        LOG.info("backend.host {}", grpcHost);
        channel = ManagedChannelBuilder
                .forTarget(grpcHost)
                .usePlaintext()
                .build();
        stub = RoomMessagesStreamingServiceGrpc.newBlockingStub(channel);
        LOG.debug("{} instance created", this);
    }

    public void stopUpdating() {
        LOG.info("Shutting down");
        channel.shutdown();
    }

    public void startUpdating() {
        LOG.info("GrpcClient, start updating");
        RoomRequest request = RoomRequest.newBuilder()
                .setUserId(form.getCurrentUser().getId())
                .setRoomId(form.getCurrentRoom().getId())
                .build();
        Iterator<RoomMessagesResponse> messagesResponseIterator = stub.messageStreaming(request);
        LOG.info("Request sent {}", request);
        RoomMessagesResponse response;
        while (messagesResponseIterator.hasNext()) {
            response = messagesResponseIterator.next();
            LOG.info("Received new message {}", response.getMessageProto());
            form.addMessage(MessageDto.fromProto(response.getMessageProto()));
        }
    }
}
