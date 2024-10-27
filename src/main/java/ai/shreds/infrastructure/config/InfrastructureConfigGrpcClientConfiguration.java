package ai.shreds.infrastructure.config; 
  
 import org.springframework.context.annotation.Configuration; 
 import org.springframework.context.annotation.Bean; 
 import org.springframework.beans.factory.annotation.Value; 
  
 import ai.shreds.domain.ports.DomainPortSmsGatewayClient; 
 import ai.shreds.infrastructure.external_services.InfrastructureClientSmsGatewayClientImpl; 
 import io.grpc.ManagedChannel; 
 import io.grpc.ManagedChannelBuilder; 
 import ai.shreds.grpc.SmsGatewayServiceGrpc; 
 import ai.shreds.grpc.SmsGatewayServiceGrpc.SmsGatewayServiceStub; 
  
 @Configuration 
 public class InfrastructureConfigGrpcClientConfiguration { 
  
     @Value("${sms.gateway.host}") 
     private String smsGatewayHost; 
  
     @Value("${sms.gateway.port}") 
     private int smsGatewayPort; 
  
     @Bean 
     public DomainPortSmsGatewayClient smsGatewayClient() { 
         // Build the gRPC channel to connect to the SMS Gateway Integration Service 
         ManagedChannel channel = ManagedChannelBuilder 
                 .forAddress(smsGatewayHost, smsGatewayPort) 
                 .usePlaintext() 
                 .build(); 
  
         // Create the gRPC stub for the SmsGatewayService 
         SmsGatewayServiceStub smsGatewayStub = SmsGatewayServiceGrpc.newStub(channel); 
  
         // Instantiate the InfrastructureClientSmsGatewayClientImpl with the stub 
         return new InfrastructureClientSmsGatewayClientImpl(smsGatewayStub); 
     } 
 } 
 