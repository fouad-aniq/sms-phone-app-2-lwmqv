package ai.shreds.adapter.exceptions; 
  
 import org.springframework.http.HttpStatus; 
 import org.springframework.http.ResponseEntity; 
 import org.springframework.web.bind.annotation.ControllerAdvice; 
 import org.springframework.web.bind.annotation.ExceptionHandler; 
  
 import java.util.HashMap; 
 import java.util.Map; 
  
 @ControllerAdvice 
 public class AdapterGlobalExceptionHandler { 
  
     @ExceptionHandler(Exception.class) 
     public ResponseEntity<Object> handleException(Exception ex) { 
         Map<String, Object> body = new HashMap<>(); 
         body.put("message", "An unexpected error occurred."); 
         return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR); 
     } 
 } 
 