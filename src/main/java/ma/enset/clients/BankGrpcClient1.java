package ma.enset.clients;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import ma.enset.stubs.Bank;
import ma.enset.stubs.BanqueServiceGrpc;


public class BankGrpcClient1{
    public static void main(String[] args) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost" , 5555)
                .usePlaintext()
                .build();
        BanqueServiceGrpc.BanqueServiceBlockingStub blockingStub = BanqueServiceGrpc.newBlockingStub(managedChannel);
        Bank.ConvertCurencyRequest request = Bank.ConvertCurencyRequest.newBuilder()
                .setCurrencyFrom("MAD")
                .setCurrencyTo("USD")
                .setAmount(6500)
                .build();
        Bank.ConvertCurrencyResponse currencyResponse = blockingStub.convert(request);
        System.out.println(currencyResponse);
    }
}
