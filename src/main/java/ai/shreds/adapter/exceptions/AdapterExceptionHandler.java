package ai.shreds.adapter.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AdapterExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(AdapterExceptionHandler.class);

    public void handleException(Exception e) {
        // Log the exception details
        logger.error("An exception occurred in Adapter layer: {}", e.getMessage(), e);
        
        // Additional exception handling logic can be implemented here
        // For example, sending an alert or notification
    }
}