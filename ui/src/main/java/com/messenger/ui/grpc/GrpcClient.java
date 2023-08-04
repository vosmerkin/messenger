package com.messenger.ui.grpc;

import com.messenger.common.dto.MessageDto;
import com.messenger.common.dto.RoomDto;
import com.messenger.common.dto.UserDto;
import com.messenger.ui.form.StartForm;
import com.messenger.ui.services.Addresses;
import com.messenger.ui.services.UiAction;
import grpc_generated.RoomMessagesResponse;
import grpc_generated.RoomMessagesStreamingServiceGrpc;
import grpc_generated.RoomRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

public class GrpcClient {
    private String address = Addresses.GRPC_ADDRESS;
    private ManagedChannel channel;
    private final StartForm form;
    private final List<MessageDto> currentRoomMessageList;
    private final RoomMessagesStreamingServiceGrpc.RoomMessagesStreamingServiceBlockingStub stub;
    private RoomRequest request;
    private final UiAction uiAction;

    public GrpcClient(StartForm form) {
        this.form = form;
        channel = ManagedChannelBuilder.forTarget(address)
                .usePlaintext()
                .build();
        stub = RoomMessagesStreamingServiceGrpc.newBlockingStub(channel);
        this.currentRoomMessageList = form.getCurrentRoomMessageList();
        this.uiAction = form.getUiAction();
    }

    public void requestStreaming(UserDto user, RoomDto room) {
        request = RoomRequest.newBuilder()
                .setUserId(user.getId())
                .setRoomId(room.getId())
                .build();
        Iterator<RoomMessagesResponse> messagesResponseIterator = stub.messageStreaming(request);
        var messageListModel = form.getMessageListModel();
        RoomMessagesResponse message;

        System.out.println("Request sent");
        while (messagesResponseIterator.hasNext()) {
            message = messagesResponseIterator.next();

            MessageDto messageDto = uiAction.requestMessage(message.getMessageId());
            currentRoomMessageList.add(messageDto);
            messageListModel.addRow(
                    new String[]{message.getUserName(),
                            new SimpleDateFormat("HH:mm:ss").format(messageDto.getMessageDateTime()),
                            message.getMessage()});
        }

        //TODO include datetime to proto


    }

    public void stopStreaming() {
        channel.shutdownNow();
    }


}