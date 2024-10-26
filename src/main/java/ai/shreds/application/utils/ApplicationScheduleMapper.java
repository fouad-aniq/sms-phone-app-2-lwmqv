package ai.shreds.application.utils; 
  
 import ai.shreds.shared.SharedScheduleDto; 
 import ai.shreds.domain.entities.DomainScheduleEntity; 
 import org.mapstruct.Mapper; 
 import org.mapstruct.Mapping; 
 import org.mapstruct.Named; 
  
 import java.sql.Timestamp; 
 import java.time.LocalDateTime; 
 import java.time.format.DateTimeFormatter; 
 import java.time.format.DateTimeParseException; 
  
 @Mapper(componentModel = "spring") 
 public interface ApplicationScheduleMapper { 
  
     @Mapping(source = "scheduledTime", target = "scheduledTime", qualifiedByName = "stringToTimestamp") 
     @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "stringToTimestamp") 
     @Mapping(source = "updatedAt", target = "updatedAt", qualifiedByName = "stringToTimestamp") 
     DomainScheduleEntity toDomain(SharedScheduleDto scheduleDto); 
  
     @Mapping(source = "scheduledTime", target = "scheduledTime", qualifiedByName = "timestampToString") 
     @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "timestampToString") 
     @Mapping(source = "updatedAt", target = "updatedAt", qualifiedByName = "timestampToString") 
     SharedScheduleDto toDto(DomainScheduleEntity schedule); 
  
     @Named("stringToTimestamp") 
     public static Timestamp stringToTimestamp(String dateTimeString) { 
         if (dateTimeString == null) { 
             return null; 
         } 
         try { 
             LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_DATE_TIME); 
             return Timestamp.valueOf(localDateTime); 
         } catch (DateTimeParseException e) { 
             throw new IllegalArgumentException("Invalid date format for dateTimeString: " + dateTimeString, e); 
         } 
     } 
  
     @Named("timestampToString") 
     public static String timestampToString(Timestamp timestamp) { 
         if (timestamp == null) { 
             return null; 
         } 
         LocalDateTime localDateTime = timestamp.toLocalDateTime(); 
         return localDateTime.format(DateTimeFormatter.ISO_DATE_TIME); 
     } 
 }