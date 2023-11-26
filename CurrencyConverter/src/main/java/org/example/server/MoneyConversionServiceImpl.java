package org.example.server;

import io.grpc.stub.StreamObserver;
import org.example.utility.CurrencyConverter;
import org.examples.stubs.Money;
import org.examples.stubs.MoneyConversionServiceGrpc;

import java.util.Map;



public class MoneyConversionServiceImpl extends MoneyConversionServiceGrpc.MoneyConversionServiceImplBase {

    private CurrencyConverter currencyConverter=new CurrencyConverter();

    @Override
    public void unaryConversion(Money.CurrencyConversionRequest request, StreamObserver<Money.CurrencyConversionResponse> responseObserver) {
        String from=request.getFrom();
        String to=request.getTo();
        double amount=request.getAmount();

        double output=currencyConverter.convert(amount,from,to);

        Money.CurrencyConversionResponse currencyConversionResponse= Money.CurrencyConversionResponse.newBuilder()
                .setTo(to)
                .setAmount(output)
                .build();

        responseObserver.onNext(currencyConversionResponse);
        responseObserver.onCompleted();


    }

    @Override
    public void serverStreamingConversion(Money.CurrencyConversionRequest request, StreamObserver<Money.CurrencyConversionResponse> responseObserver)  {
        String from=request.getFrom();
        double amount=request.getAmount();
        double output;

        Money.CurrencyConversionResponse response;

        Map<String ,Double>  destinations=currencyConverter.getExchangeRates().get(from.toUpperCase());
        if(destinations==null){
            responseObserver.onError(new Throwable("The From currency doesn't exist"));
        }
        else {
            for(String destination: destinations.keySet()){
                output=currencyConverter.convert(amount,from,destination);

                response= Money.CurrencyConversionResponse.newBuilder()
                        .setTo(destination)
                        .setAmount(output)
                        .build();

                responseObserver.onNext(response);

            }
            responseObserver.onCompleted();
        }

    }

    @Override
    public StreamObserver<Money.CurrencyConversionRequest> clientStreamingConversion(StreamObserver<Money.CurrencyConversionResponse> responseObserver) {

        return new StreamObserver<>() {
            int final_amount = 0;

            @Override
            public void onNext(Money.CurrencyConversionRequest currencyConversionRequest) {
                String from = currencyConversionRequest.getFrom();
                String to = currencyConversionRequest.getTo();
                double amount = currencyConversionRequest.getAmount();
                final_amount += currencyConverter.convert(amount, from, to);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                Money.CurrencyConversionResponse response = Money.CurrencyConversionResponse.newBuilder()
                        .setTo("")
                        .setAmount(final_amount)
                        .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        };
    }
}
