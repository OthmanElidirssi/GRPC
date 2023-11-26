package org.example.server;
import com.example.client.output.Chat;
import com.example.client.output.ChatServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;

public class ChatServiceImpl extends ChatServiceGrpc.ChatServiceImplBase {

    private static List<StreamObserver<Chat.ChatMessageServer>> observers=new ArrayList<>();
    @Override
    public StreamObserver<Chat.ChatMessage> chat(StreamObserver<Chat.ChatMessageServer> responseObserver) {
        observers.add(responseObserver);
        return new StreamObserver<Chat.ChatMessage>() {
            @Override
            public void onNext(Chat.ChatMessage chatMessage) {

                Chat.ChatMessageServer msg= Chat.ChatMessageServer.newBuilder()
                        .setFrom(chatMessage.getFrom())
                        .setMessage(chatMessage.getMessage())
                        .build();
                observers.stream()
                        .forEach(chatMessageServerStreamObserver -> {

                            chatMessageServerStreamObserver.onNext(msg);
                        });

            }

            @Override
            public void onError(Throwable throwable) {

                observers.remove(responseObserver);

            }

            @Override
            public void onCompleted() {
                observers.remove(responseObserver);
            }
        };
    }
}
