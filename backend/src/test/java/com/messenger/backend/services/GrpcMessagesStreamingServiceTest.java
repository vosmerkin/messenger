package com.messenger.backend.services;

import com.messenger.backend.entity.MessageEntity;
import com.messenger.backend.entity.RoomEntity;
import com.messenger.backend.entity.UserEntity;
import grpc_generated.RoomMessagesResponse;
import grpc_generated.RoomMessagesStreamingServiceGrpc;
import grpc_generated.RoomRequest;
import io.grpc.*;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;

import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.*;

import io.grpc.testing.GrpcCleanupRule;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GrpcMessagesStreamingServiceTest {
    @Rule
    public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();
    private Server inProcessServer;
    private ManagedChannel inProcessChannel;
    private RoomMessagesStreamingServiceGrpc.RoomMessagesStreamingServiceBlockingStub stub;

    @Mock
    private MessageService messageService;
//
//    private RouteGuideServer server;
//    private ManagedChannel inProcessChannel;
//    private Collection<Feature> features;

    @BeforeEach
    public void setUp() throws Exception {
        String serverName = InProcessServerBuilder.generateName();
        inProcessServer = InProcessServerBuilder.forName(serverName)
                .directExecutor() // directExecutor is fine for unit tests
                .addService(new GrpcMessagesStreamingService(messageService))
                .build()
                .start();
        System.out.println("Service started");

        inProcessChannel = grpcCleanup.register(
                InProcessChannelBuilder
                        .forName(serverName)
                        .directExecutor()
                        .build()
        );
    }

    @AfterEach
    public void tearDown() throws Exception {
        inProcessServer.shutdownNow();
        System.out.println("Service stopped");
    }


    //broadcastNewMessage tests
    //1 - check if all clients receive broadcasted message
    //2 - check if broadcasting null message
    //3 - (almost impossible) check if trying to broadcast to room,
    // that is not present in streamObserverMap (the list of responseObservers
    // for each ObserverKey(int userId, int roomId))

    @Test
    void broadcastNewMessageTest() {
    }


    //messageStreaming tests
    //messageStreaming(RoomRequest request, StreamObserver<RoomMessagesResponse> responseObserver)
    //method receives request (valid, or invalid)
    //method should update streamObserverMap
    //method should call messageService.getByRoomId(request.getRoomId())
    //upon getting messages list (valid, or invalid) method should
    // call responseObserver.onNext(message.toRoomMessagesResponse()) for every message

    @DisplayName("Testing GrpcMessagesStreamingService/messageStreaming")
    @Test
    void messageStreamingTest() {

        int userId1 = 1;
        int userId2 = 2;
        int messageId1 = 1;
        int messageId2 = 2;
        String userName1 = "testUser1";
        String userName2 = "testUser2";
        String messageText1 = "message1";
        String messageText2 = "message2";
        int roomId = 1;
        String roomName = "testRoom";
        Instant POINT_IN_TIME = OffsetDateTime.parse("2010-12-31T23:59:59Z").toInstant();
        RoomEntity testRoom = new RoomEntity(roomId, roomName, Collections.emptySet());
        UserEntity testUser1 = new UserEntity(userId1, userName1, Date.from(POINT_IN_TIME), true, Collections.emptySet());
        UserEntity testUser2 = new UserEntity(userId2, userName2, Date.from(POINT_IN_TIME), true, Collections.emptySet());
        MessageEntity messageEntity1 = new MessageEntity(messageId1, Date.from(POINT_IN_TIME), messageText1, testRoom, testUser1);
        MessageEntity messageEntity2 = new MessageEntity(messageId2, Date.from(POINT_IN_TIME), messageText2, testRoom, testUser2);
        List<MessageEntity> roomMessagesList = new ArrayList<>();
        roomMessagesList.add(messageEntity1);
        roomMessagesList.add(messageEntity2);
        RoomRequest request = RoomRequest.newBuilder()
                .setUserId(userId1)
                .setRoomId(roomId)
                .build();
        stub = RoomMessagesStreamingServiceGrpc.newBlockingStub(inProcessChannel);


        when(messageService.getByRoomId(roomId)).thenReturn(roomMessagesList);      //setting behaviour of mocked MessageService object

        Iterator<RoomMessagesResponse> messagesResponseIterator = stub.messageStreaming(request);               //calling service
        RoomMessagesResponse response;
        for (int i = 0; messagesResponseIterator.hasNext(); i++) {
            response = messagesResponseIterator.next();
            assertThat(MessageEntity.fromProto(response.getMessageProto())).isIn(roomMessagesList);        //asserting results
            System.out.println(response);
            if (i == 1) {
                break;
            }
        }


        verify(messageService).getByRoomId(roomId);    //verifying that business logic was called


    }

}
