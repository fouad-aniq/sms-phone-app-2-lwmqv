package ai.shreds.application.services; 
  
 import ai.shreds.application.ports.ApplicationOutputPortSMSGatewayClientPort; 
 import ai.shreds.shared.SharedSMSGatewayRequest; 
 import ai.shreds.shared.SharedSMSGatewayResponse; 
 import org.springframework.stereotype.Service; 
 import org.springframework.web.client.RestTemplate; 
 import org.springframework.web.client.HttpStatusCodeException; 
 import org.springframework.web.client.ResourceAccessException; 
 import org.springframework.http.ResponseEntity; 
 import org.springframework.http.HttpEntity; 
 import org.springframework.http.HttpHeaders; 
 import org.springframework.http.MediaType; 
  
 @Service 
 public class ApplicationServiceSMSGatewayClientService implements ApplicationOutputPortSMSGatewayClientPort { 
  
     private static final String SMS_GATEWAY_URL = "https://sms-gateway-service/api/v1/messages/send"; 
  
     private final RestTemplate restTemplate; 
  
     public ApplicationServiceSMSGatewayClientService(RestTemplate restTemplate) { 
         this.restTemplate = restTemplate; 
     } 
  
     @Override 
     public SharedSMSGatewayResponse sendMessage(SharedSMSGatewayRequest request) { 
         try { 
             HttpHeaders headers = new HttpHeaders(); 
             headers.setContentType(MediaType.APPLICATION_JSON); 
  
             HttpEntity<SharedSMSGatewayRequest> entity = new HttpEntity<>(request, headers); 
  
             ResponseEntity<SharedSMSGatewayResponse> response = restTemplate.postForEntity(SMS_GATEWAY_URL, entity, SharedSMSGatewayResponse.class); 
  
             return response.getBody(); 
         } catch (HttpStatusCodeException ex) { 
             SharedSMSGatewayResponse errorResponse = new SharedSMSGatewayResponse(); 
             errorResponse.setStatus("failed"); 
             errorResponse.setMessageId(request.getMessageId()); 
             errorResponse.setDetails(ex.getResponseBodyAsString()); 
             return errorResponse; 
         } catch (ResourceAccessException ex) { 
             SharedSMSGatewayResponse errorResponse = new SharedSMSGatewayResponse(); 
             errorResponse.setStatus("failed"); 
             errorResponse.setMessageId(request.getMessageId()); 
             errorResponse.setDetails("Resource access exception: " + ex.getMessage()); 
             return errorResponse; 
         } catch (Exception ex) { 
             SharedSMSGatewayResponse errorResponse = new SharedSMSGatewayResponse(); 
             errorResponse.setStatus("failed"); 
             errorResponse.setMessageId(request.getMessageId()); 
             errorResponse.setDetails("An unexpected error occurred: " + ex.getMessage()); 
             return errorResponse; 
         } 
     } 
 } 
 