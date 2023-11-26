package org.example.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.example.server.ChatServiceImpl;


import java.io.IOException;

public class ChatServer {

    public static void main(String[] args) throws Exception {

        Server server = ServerBuilder.forPort(8087)
                .addService(new ChatServiceImpl())
                .build();

        server.start();
        System.out.println("Sever is runnig on port " + server.getPort());
        server.awaitTermination();
    }
}
