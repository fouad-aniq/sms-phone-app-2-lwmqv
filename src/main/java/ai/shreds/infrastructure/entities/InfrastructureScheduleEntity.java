package ai.shreds.infrastructure.entities; 
  
 import java.time.OffsetDateTime; 
 import java.util.UUID; 
  
 import javax.persistence.Column; 
 import javax.persistence.Entity; 
 import javax.persistence.Id; 
 import javax.persistence.PrePersist; 
 import javax.persistence.PreUpdate; 
 import javax.persistence.Table; 
  
 import lombok.AllArgsConstructor; 
 import lombok.Builder; 
 import lombok.Data; 
 import lombok.NoArgsConstructor; 
  
 @Entity 
 @Table(name = "schedules") 
 @Data 
 @Builder 
 @NoArgsConstructor 
 @AllArgsConstructor 
 public class InfrastructureScheduleEntity { 
  
     @Id 
     @Column(name = "schedule_id", nullable = false, updatable = false) 
     private UUID scheduleId; 
  
     @Column(name = "message_content", nullable = false, columnDefinition = "TEXT") 
     private String messageContent; 
  
     @Column(name = "recipient", nullable = false, length = 255) 
     private String recipient; 
  
     @Column(name = "scheduled_time", nullable = false) 
     private OffsetDateTime scheduledTime; 
  
     @Column(name = "status", nullable = false, length = 50) 
     private String status; 
  
     @Column(name = "created_at", nullable = false) 
     private OffsetDateTime createdAt; 
  
     @Column(name = "updated_at", nullable = false) 
     private OffsetDateTime updatedAt; 
  
     @PrePersist 
     protected void onCreate() { 
         OffsetDateTime now = OffsetDateTime.now(); 
         this.createdAt = now; 
         this.updatedAt = now; 
         if (this.scheduleId == null) { 
             this.scheduleId = UUID.randomUUID(); 
         } 
     } 
  
     @PreUpdate 
     protected void onUpdate() { 
         this.updatedAt = OffsetDateTime.now(); 
     } 
  
 } 
 