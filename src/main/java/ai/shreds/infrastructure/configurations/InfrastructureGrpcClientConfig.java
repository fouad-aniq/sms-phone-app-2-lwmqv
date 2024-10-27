package ai.shreds.infrastructure.configurations;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ai.shreds.infrastructure.external_services.generated.SmsGatewayServiceGrpc;

@Configuration
public class InfrastructureGrpcClientConfig {

    @Value("${sms.gateway.grpc.host}")
    private String smsGatewayGrpcHost;

    @Value("${sms.gateway.grpc.port}")
    private int smsGatewayGrpcPort;

    public void configureGrpcClients() {
        // Method included as per UML specification. Configuration is handled in @Bean methods.
    }

    @Bean
    public ManagedChannel smsGatewayManagedChannel() {
        return ManagedChannelBuilder
                .forAddress(smsGatewayGrpcHost, smsGatewayGrpcPort)
                .usePlaintext() // Use .useTransportSecurity() for SSL/TLS in production environments
                .build();
    }

    @Bean
    public SmsGatewayServiceGrpc.SmsGatewayServiceBlockingStub smsGatewayServiceBlockingStub(ManagedChannel smsGatewayManagedChannel) {
        return SmsGatewayServiceGrpc.newBlockingStub(smsGatewayManagedChannel);
    }
}