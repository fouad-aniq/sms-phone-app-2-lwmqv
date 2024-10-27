package ai.shreds.application.ports;

import ai.shreds.shared.dto.SharedSMSGatewayRequest;
import ai.shreds.shared.SharedSMSGatewayResponse;

/**
 * Outbound port interface for communicating with the SMS Gateway Integration Service.
 * This interface defines the contract for sending messages to the SMS Gateway.
 */
public interface ApplicationOutputPortSMSGatewayClientPort {
    /**
     * Sends a message to the SMS Gateway Integration Service.
     * 
     * @param request the SMS gateway request containing message details
     * @return the response from the SMS Gateway Integration Service
     */
    SharedSMSGatewayResponse sendMessage(SharedSMSGatewayRequest request);
}