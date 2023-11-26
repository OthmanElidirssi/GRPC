package org.example.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.examples.stubs.Money;
import org.examples.stubs.MoneyConversionServiceGrpc;

import java.util.Scanner;

public class CurrencyClient {

    public static void main(String[] args) {


        ManagedChannel managedChannel= ManagedChannelBuilder.forAddress("localhost",8087)
                .usePlaintext()
                .build();


        /*MoneyConversionServiceGrpc.MoneyConversionServiceBlockingStub stub=MoneyConversionServiceGrpc.newBlockingStub(managedChannel);

        Money.CurrencyConversionRequest currencyConversionRequest= Money.CurrencyConversionRequest.newBuilder()
                .setFrom("eur")
                .setTo("usd")
                .setAmount(8.8)
                .build();

        Money.CurrencyConversionResponse response=stub.unaryConversion(currencyConversionRequest);

        System.out.println(currencyConversionRequest);


        System.out.println("--------------------------");

        System.out.println(response);

        */

        Money.CurrencyConversionRequest currencyConversionRequest= Money.CurrencyConversionRequest.newBuilder()
                .setFrom("eur")
                .setTo("usd")
                .setAmount(205)
                .build();


        MoneyConversionServiceGrpc.MoneyConversionServiceStub stub = MoneyConversionServiceGrpc.newStub(managedChannel);

        stub.serverStreamingConversion(currencyConversionRequest, new StreamObserver<>() {
            @Override
            public void onNext(Money.CurrencyConversionResponse currencyConversionResponse) {
                System.out.println(currencyConversionResponse);
            }

            @Override
            public void onError(Throwable throwable) {

                System.out.println(throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                //do nothing
            }
        });

        Scanner scanner = new Scanner(System.in);
        System.out.println("");
        scanner.nextLine(); // Wait for user input
        scanner.close();

             }
}
