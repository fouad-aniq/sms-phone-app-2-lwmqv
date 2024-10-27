package ai.shreds.adapter.primary; 
  
 import ai.shreds.application.ports.ApplicationHandleAcknowledgmentInputPort; 
 import ai.shreds.shared.SharedAcknowledgmentDTO; 
 import ai.shreds.shared.SharedErrorNotificationDTO; 
 import ai.shreds.proto.AcknowledgmentServiceGrpc; 
 import ai.shreds.proto.DeliveryAcknowledgmentRequest; 
 import ai.shreds.proto.DeliveryAcknowledgmentResponse; 
 import ai.shreds.proto.ErrorNotificationRequest; 
 import ai.shreds.proto.ErrorNotificationResponse; 
 import io.grpc.stub.StreamObserver; 
 import org.lognet.springboot.grpc.GRpcService; 
 import org.springframework.beans.factory.annotation.Autowired; 
  
 import java.util.Date; 
  
 @GRpcService 
 public class AdapterAcknowledgmentGrpcService extends AcknowledgmentServiceGrpc.AcknowledgmentServiceImplBase { 
  
     @Autowired 
     private ApplicationHandleAcknowledgmentInputPort acknowledgmentInputPort; 
  
     @Override 
     public void handleDeliveryAcknowledgment(DeliveryAcknowledgmentRequest request, StreamObserver<DeliveryAcknowledgmentResponse> responseObserver) { 
         // Map the gRPC request to SharedAcknowledgmentDTO 
         SharedAcknowledgmentDTO acknowledgment = SharedAcknowledgmentDTO.builder() 
                 .messageId(request.getMessageId()) 
                 .dispatchStatus(request.getDispatchStatus()) 
                 .deliveryTimestamp(new Date(request.getDeliveryTimestamp())) 
                 .details(request.getDetails()) 
                 .build(); 
  
         // Process the acknowledgment 
         handleDeliveryAcknowledgment(acknowledgment); 
  
         // Build and send the response 
         DeliveryAcknowledgmentResponse response = DeliveryAcknowledgmentResponse.newBuilder() 
                 .setStatus("Acknowledgment received") 
                 .build(); 
         responseObserver.onNext(response); 
         responseObserver.onCompleted(); 
     } 
  
     @Override 
     public void handleErrorNotification(ErrorNotificationRequest request, StreamObserver<ErrorNotificationResponse> responseObserver) { 
         // Map the gRPC request to SharedErrorNotificationDTO 
         SharedErrorNotificationDTO notification = SharedErrorNotificationDTO.builder() 
                 .messageId(request.getMessageId()) 
                 .dispatchStatus(request.getDispatchStatus()) 
                 .errorCode(request.getErrorCode()) 
                 .errorMessage(request.getErrorMessage()) 
                 .build(); 
  
         // Process the error notification 
         handleErrorNotification(notification); 
  
         // Build and send the response 
         ErrorNotificationResponse response = ErrorNotificationResponse.newBuilder() 
                 .setStatus("Error notification received") 
                 .build(); 
         responseObserver.onNext(response); 
         responseObserver.onCompleted(); 
     } 
  
     public void handleDeliveryAcknowledgment(SharedAcknowledgmentDTO acknowledgment) { 
         acknowledgmentInputPort.processAcknowledgment(acknowledgment); 
     } 
  
     public void handleErrorNotification(SharedErrorNotificationDTO notification) { 
         acknowledgmentInputPort.processErrorNotification(notification); 
     } 
 } 
 