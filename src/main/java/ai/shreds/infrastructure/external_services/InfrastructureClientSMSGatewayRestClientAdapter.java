package ai.shreds.infrastructure.external_services;

import ai.shreds.application.ports.ApplicationOutputPortSMSGatewayClientPort;
import ai.shreds.shared.SharedSMSGatewayRequest;
import ai.shreds.shared.SharedSMSGatewayResponse;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;

@Component
@Slf4j
public class InfrastructureClientSMSGatewayRestClientAdapter implements ApplicationOutputPortSMSGatewayClientPort {

    @Value("${sms.gateway.url}")
    private String smsGatewayUrl;

    private final RestTemplate restTemplate;

    public InfrastructureClientSMSGatewayRestClientAdapter() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public SharedSMSGatewayResponse sendMessage(SharedSMSGatewayRequest request) {
        String url = smsGatewayUrl + "/api/v1/messages/send";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SharedSMSGatewayRequest> httpEntity = new HttpEntity<>(request, headers);

        try {
            log.info("Sending message to SMS Gateway: messageId={}, recipient={}", request.getMessageId(), request.getRecipient());
            ResponseEntity<SharedSMSGatewayResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, SharedSMSGatewayResponse.class);
            log.info("Received response from SMS Gateway: status={}, messageId={}", responseEntity.getBody().getStatus(), responseEntity.getBody().getMessageId());
            return responseEntity.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            String responseBody = e.getResponseBodyAsString();
            log.error("Error response from SMS Gateway: statusCode={}, responseBody={}", e.getStatusCode(), responseBody);
            SharedSMSGatewayResponse errorResponse;
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                errorResponse = objectMapper.readValue(responseBody, SharedSMSGatewayResponse.class);
            } catch (IOException ioException) {
                log.error("Failed to parse error response: {}, exception: {}", responseBody, ioException.getMessage());
                errorResponse = new SharedSMSGatewayResponse();
                errorResponse.setStatus("failed");
                errorResponse.setMessageId(request.getMessageId());
                errorResponse.setDetails("Error: " + e.getStatusCode() + " " + e.getStatusCode().getReasonPhrase());
            }
            return errorResponse;
        } catch (Exception e) {
            log.error("Exception occurred while sending message: {}", e.getMessage(), e);
            SharedSMSGatewayResponse errorResponse = new SharedSMSGatewayResponse();
            errorResponse.setStatus("failed");
            errorResponse.setMessageId(request.getMessageId());
            errorResponse.setDetails(e.getMessage());
            return errorResponse;
        }
    }
}