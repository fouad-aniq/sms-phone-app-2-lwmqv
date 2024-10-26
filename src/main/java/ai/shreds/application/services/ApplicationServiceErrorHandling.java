package ai.shreds.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.shreds.domain.services.DomainServiceErrorHandling;

@Service
public class ApplicationServiceErrorHandling {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationServiceErrorHandling.class);

    private final DomainServiceErrorHandling domainServiceErrorHandling;

    @Autowired
    public ApplicationServiceErrorHandling(DomainServiceErrorHandling domainServiceErrorHandling) {
        this.domainServiceErrorHandling = domainServiceErrorHandling;
    }

    public void handleError(Exception exception, Object context) {
        logError(exception);
        if (shouldRetry(exception)) {
            // Retry logic could be implemented here if required
        } else {
            domainServiceErrorHandling.handleNonRecoverableError(exception, context);
        }
    }

    public void logError(Exception exception) {
        logger.error("An error occurred: {}", exception.getMessage(), exception);
    }

    public boolean shouldRetry(Exception exception) {
        return domainServiceErrorHandling.shouldRetry(exception);
    }
}