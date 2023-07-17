package com.messenger.backend.grpc;

import com.messenger.backend.controllers.MessageController;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class GrpcServer {
    private static final Logger log = LoggerFactory.getLogger(GrpcServer.class);
    Server server;

    int port;

    public GrpcServer(int port) {
        server = ServerBuilder.forPort(this.port = port).addService(new RoomMessagesStreamingServiceImplBaseImpl()).build();
        log.info("Grpc server created on port " + port);
    }

    public void start() throws IOException {
        server.start();
        log.info("Grpc server started on port " + port);
    }

    public void shutdown() throws InterruptedException {
        server.shutdownNow();
        log.info("Grpc server shutdown");
        server.awaitTermination();
    }


}
