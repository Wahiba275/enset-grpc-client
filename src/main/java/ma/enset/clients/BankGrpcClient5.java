package ma.enset.clients;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import ma.enset.stubs.Bank;
import ma.enset.stubs.BanqueServiceGrpc;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class BankGrpcClient5 {
    public static void main(String[] args) throws IOException {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost" , 5555)
                .usePlaintext()
                .build();
        BanqueServiceGrpc.BanqueServiceStub asybcStub= BanqueServiceGrpc.newStub(managedChannel);
        Bank.ConvertCurencyRequest request = Bank.ConvertCurencyRequest.newBuilder()
                .setCurrencyFrom("MAD")
                .setCurrencyTo("USD")
                .setAmount(6500)
                .build();
        StreamObserver<Bank.ConvertCurencyRequest> performStream =
                asybcStub.performStream(new StreamObserver<Bank.ConvertCurrencyResponse>() {
            @Override
            public void onNext(Bank.ConvertCurrencyResponse convertCurrencyResponse) {
                System.out.println("**************");
                System.out.println(convertCurrencyResponse);
                System.out.println("**********");
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                System.out.println("END.............");
            }
        });
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            int counter =0;
            @Override
            public void run() {
                Bank.ConvertCurencyRequest curencyRequest = Bank.ConvertCurencyRequest.newBuilder()
                        .setAmount(Math.random()*7000)
                        .build();
                performStream.onNext(curencyRequest);
                System.out.println("--------counter = "+counter);
                ++counter;
                if(counter==20){
                    performStream.onCompleted();
                    timer.cancel();
                }
            }
        }, 1000, 1000);


        System.out.println(".............?");
        System.in.read();
    }
}
