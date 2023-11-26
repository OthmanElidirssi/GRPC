package org.example.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcServer {

    public static void main(String[] args) throws Exception {

        Server server = ServerBuilder.forPort(8087)
                .addService(new GreetinServiceImpl())
                .build();

        server.start();
        System.out.println("Sever is runnig on port " + server.getPort());
        server.awaitTermination();

    }
}
