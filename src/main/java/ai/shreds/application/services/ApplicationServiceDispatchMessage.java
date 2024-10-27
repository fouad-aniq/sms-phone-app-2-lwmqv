package ai.shreds.application.services;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ai.shreds.application.ports.ApplicationInputPortDispatchMessage;
import ai.shreds.application.mappers.ApplicationProcessedMessageMapper;
import ai.shreds.shared.SharedProcessedMessageDTO;
import ai.shreds.domain.entities.DomainEntityProcessedMessage;
import ai.shreds.domain.services.DomainServiceMessageDispatch;
import ai.shreds.application.exceptions.ApplicationExceptionDispatchMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class ApplicationServiceDispatchMessage implements ApplicationInputPortDispatchMessage {

    private final ApplicationProcessedMessageMapper mapper;
    private final DomainServiceMessageDispatch domainServiceMessageDispatch;
    private static final Logger logger = LoggerFactory.getLogger(ApplicationServiceDispatchMessage.class);

    @Override
    public void dispatchMessage(SharedProcessedMessageDTO messageDTO) {
        try {
            if (!messageDTO.getValidationStatus() || !messageDTO.getPreparedForDispatch()) {
                throw new ApplicationExceptionDispatchMessage("Message is not valid or not prepared for dispatch");
            }
            DomainEntityProcessedMessage domainMessage = mapper.toDomain(messageDTO);
            domainServiceMessageDispatch.sendMessage(domainMessage);
        } catch (Exception e) {
            logger.error("Failed to dispatch message: {}", e.getMessage(), e);
            throw new ApplicationExceptionDispatchMessage("Failed to dispatch message", e);
        }
    }
}