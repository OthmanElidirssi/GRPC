package org.example.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;



public class CurrencyServer {

    public static void main(String[] args) throws Exception {
        Server server= ServerBuilder.forPort(8087)
                .addService(new MoneyConversionServiceImpl())
                .build();

        server.start();

        System.out.println("Server is running on port:"+server.getPort());
        server.awaitTermination();////


    }
}
