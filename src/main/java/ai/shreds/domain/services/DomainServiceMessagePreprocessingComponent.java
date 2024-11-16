package ai.shreds.domain.services;

import ai.shreds.domain.entities.DomainEntitySMSMessage;
import ai.shreds.domain.ports.DomainPortSMSMessageRepositoryPort;
import ai.shreds.shared.SharedEnumMessageStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DomainServiceMessagePreprocessingComponent {
    
    private final DomainPortSMSMessageRepositoryPort smsMessageRepositoryPort;
    
    public void prepareMessageForRouting(DomainEntitySMSMessage message) {
        // Ensure metadata is initialized
        Map<String, String> metadata = message.getMetadata();
        if (metadata == null) {
            metadata = new HashMap<>();
            message.setMetadata(metadata);
        }
        
        // Add routing metadata
        metadata.put("routingKey", "defaultRoutingKey");
        metadata.put("priority", "normal");
        
        // Update message status
        message.setStatus(SharedEnumMessageStatusEnum.VALIDATED);
        
        // Update updatedAt timestamp
        message.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        
        // Save or update the message
        smsMessageRepositoryPort.save(message);
    }
    
    public void formatMessageContent(DomainEntitySMSMessage message) {
        // Format message content as needed
        String content = message.getContent();
        if (content != null) {
            // Trim whitespace
            content = content.trim();
            
            // Escape special characters
            content = content.replaceAll("[\\t\\n\\r]", " ");
            
            // Normalize content (e.g., remove non-ASCII characters)
            content = content.replaceAll("[^\\x20-\\x7E]", "");
            
            // Update content
            message.setContent(content);
        }
    }
}
