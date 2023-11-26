package org.example.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.example.grpc.GreetingServiceGrpc;
import org.example.grpc.HelloRequest;
import org.example.grpc.HelloResponse;

public class Client {
    public static void main(String[] args) {

       ManagedChannel channel= ManagedChannelBuilder.forAddress("localhost",8087)
                .usePlaintext()
                .build();
        GreetingServiceGrpc.GreetingServiceBlockingStub stub=GreetingServiceGrpc.newBlockingStub(channel);
       HelloResponse response=stub.greeting(
                HelloRequest.newBuilder()
                        .setFirstname("Othman")
                        .setAge(21)
                        .addHobbies("Travel")
                        .addHobbies("Code")
                        .putBagOfTricks("Live coding","Not good")
                        .build()
        );
        System.out.println(response);

    }
}


