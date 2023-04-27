package ma.enset.clients;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import ma.enset.stubs.Bank;
import ma.enset.stubs.BanqueServiceGrpc;
import java.io.IOException;


public class BankGrpcClient2 {
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
        asybcStub.convert(request, new StreamObserver<Bank.ConvertCurrencyResponse>() {
            @Override
            public void onNext(Bank.ConvertCurrencyResponse convertCurrencyResponse) {
                System.out.println("********************");
                System.out.println(convertCurrencyResponse);
                System.out.println("********************");

            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("END.......");
            }
        });
        System.out.println(".............?");
        System.in.read();
    }
}
