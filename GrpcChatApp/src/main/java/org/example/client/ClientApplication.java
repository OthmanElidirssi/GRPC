package org.example.client;
import com.example.client.output.Chat;
import com.example.client.output.ChatServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientApplication extends Application {


    private ObservableList<String> messages= FXCollections.observableArrayList();
    private ListView<String> messagesView=new ListView<>();
    private TextField name=new TextField("name");
    private TextField message=new TextField();
    private Button send=new Button("Send");



    public void init(Stage primaryStage) throws Exception {
        super.init();
        messagesView.setItems(messages);

        BorderPane pane=new BorderPane();
        pane.setLeft(name);
        pane.setCenter(message);
        pane.setRight(send);

        BorderPane root=new BorderPane();
        root.setCenter(messagesView);
        root.setBottom(pane);

        primaryStage.setTitle("gRPC Chat");
        primaryStage.setScene(new Scene(root,480,320));
        primaryStage.show();

    }

    @Override
    public void start(Stage stage) throws Exception {
        init(stage);

        ManagedChannel channel= ManagedChannelBuilder.forAddress("localhost",8087)
                .usePlaintext()
                .build();
        ChatServiceGrpc.ChatServiceStub chatServiceStub=ChatServiceGrpc.newStub(channel);

       StreamObserver<Chat.ChatMessage> toServer= chatServiceStub.chat(new StreamObserver<Chat.ChatMessageServer>() {
            @Override
            public void onNext(Chat.ChatMessageServer chatMessageServer) {
                Platform.runLater(()->{
                    messages.add(String.format("%s: %s",chatMessageServer.getFrom(),chatMessageServer.getMessage()));
                });
            }

            @Override
            public void onError(Throwable throwable) {

                throwable.printStackTrace();

            }

            @Override
            public void onCompleted() {

            }
        });

        send.setOnAction(actionEvent -> {

            toServer.onNext(
                    Chat.ChatMessage.newBuilder()
                            .setFrom(name.getText())
                            .setMessage(message.getText())
                            .build()
            );

        });


    }
    public static void main(String[] args) {
        launch();
    }

}
