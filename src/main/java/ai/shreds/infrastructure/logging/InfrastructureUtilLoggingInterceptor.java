package ai.shreds.infrastructure.logging; 
  
 import io.grpc.CallOptions; 
 import io.grpc.Channel; 
 import io.grpc.ClientCall; 
 import io.grpc.ClientInterceptor; 
 import io.grpc.Metadata; 
 import io.grpc.MethodDescriptor; 
 import net.devh.boot.grpc.client.interceptor.GrpcGlobalClientInterceptor; 
 import org.slf4j.Logger; 
 import org.slf4j.LoggerFactory; 
  
 @GrpcGlobalClientInterceptor 
 public class InfrastructureUtilLoggingInterceptor implements ClientInterceptor { 
  
     private static final Logger LOGGER = LoggerFactory.getLogger(InfrastructureUtilLoggingInterceptor.class); 
  
     public void logDatabaseOperation(String operation, String details) { 
         LOGGER.info("Database Operation: {}, Details: {}", operation, details); 
     } 
  
     public void logGrpcOperation(String operation, String details) { 
         LOGGER.info("gRPC Operation: {}, Details: {}", operation, details); 
     } 
  
     @Override 
     public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall( 
             MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) { 
  
         logGrpcOperation("Calling gRPC method", method.getFullMethodName()); 
  
         ClientCall<ReqT, RespT> clientCall = next.newCall(method, callOptions); 
  
         return new ClientCall<ReqT, RespT>() { 
  
             @Override 
             public void start(Listener<RespT> responseListener, Metadata headers) { 
                 ClientCall.Listener<RespT> listener = new ClientCall.Listener<RespT>() { 
                     @Override 
                     public void onMessage(RespT message) { 
                         logGrpcOperation("Received response from gRPC method", method.getFullMethodName()); 
                         LOGGER.debug("Response message: {}", message); 
                         responseListener.onMessage(message); 
                     } 
  
                     @Override 
                     public void onHeaders(Metadata headers) { 
                         LOGGER.debug("Received headers: {}", headers); 
                         responseListener.onHeaders(headers); 
                     } 
  
                     @Override 
                     public void onClose(io.grpc.Status status, Metadata trailers) { 
                         logGrpcOperation("gRPC call closed with status", status.toString()); 
                         LOGGER.debug("Trailers: {}", trailers); 
                         responseListener.onClose(status, trailers); 
                     } 
  
                     @Override 
                     public void onReady() { 
                         responseListener.onReady(); 
                     } 
                 }; 
                 clientCall.start(listener, headers); 
             } 
  
             @Override 
             public void request(int numMessages) { 
                 clientCall.request(numMessages); 
             } 
  
             @Override 
             public void cancel(String message, Throwable cause) { 
                 logGrpcOperation("gRPC call canceled", message); 
                 clientCall.cancel(message, cause); 
             } 
  
             @Override 
             public void halfClose() { 
                 clientCall.halfClose(); 
             } 
  
             @Override 
             public void sendMessage(ReqT message) { 
                 logGrpcOperation("Sending request to gRPC method", method.getFullMethodName()); 
                 LOGGER.debug("Request message: {}", message); 
                 clientCall.sendMessage(message); 
             } 
         }; 
     } 
 } 
 