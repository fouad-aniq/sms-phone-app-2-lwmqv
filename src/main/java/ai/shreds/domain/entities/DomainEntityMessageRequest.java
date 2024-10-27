package ai.shreds.domain.entities; 
  
 import java.util.Date; 
  
 public class DomainEntityMessageRequest { 
     private String id; 
     private String senderId; 
     private String recipientNumber; 
     private String content; 
     private Date requestTimestamp; 
     private String status; 
  
     public DomainEntityMessageRequest() { 
     } 
  
     public DomainEntityMessageRequest(String id, String senderId, String recipientNumber, String content, Date requestTimestamp, String status) { 
         this.id = id; 
         this.senderId = senderId; 
         this.recipientNumber = recipientNumber; 
         this.content = content; 
         this.requestTimestamp = requestTimestamp; 
         this.status = status; 
     } 
  
     public String getId() { 
         return id; 
     } 
  
     public void setId(String id) { 
         this.id = id; 
     } 
  
     public String getSenderId() { 
         return senderId; 
     } 
  
     public void setSenderId(String senderId) { 
         this.senderId = senderId; 
     } 
  
     public String getRecipientNumber() { 
         return recipientNumber; 
     } 
  
     public void setRecipientNumber(String recipientNumber) { 
         this.recipientNumber = recipientNumber; 
     } 
  
     public String getContent() { 
         return content; 
     } 
  
     public void setContent(String content) { 
         this.content = content; 
     } 
  
     public Date getRequestTimestamp() { 
         return requestTimestamp; 
     } 
  
     public void setRequestTimestamp(Date requestTimestamp) { 
         this.requestTimestamp = requestTimestamp; 
     } 
  
     public String getStatus() { 
         return status; 
     } 
  
     public void setStatus(String status) { 
         this.status = status; 
     } 
 } 
 