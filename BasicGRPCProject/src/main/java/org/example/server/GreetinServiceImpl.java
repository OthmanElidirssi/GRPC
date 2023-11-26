package org.example.server;

import io.grpc.stub.StreamObserver;
import org.example.grpc.GreetingServiceGrpc;
import org.example.grpc.HelloRequest;
import org.example.grpc.HelloResponse;

public class GreetinServiceImpl extends GreetingServiceGrpc.GreetingServiceImplBase {
    @Override
    public void greeting(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        System.out.println(request);
        responseObserver.onNext(
                HelloResponse.newBuilder()
                        .setGreeting("Hello " + request.getFirstname())
                        .build()
        );
        responseObserver.onCompleted();

    }
}
