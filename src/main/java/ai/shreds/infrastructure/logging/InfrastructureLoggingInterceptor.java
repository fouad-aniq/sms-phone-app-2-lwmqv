package ai.shreds.infrastructure.logging;

import io.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * InfrastructureLoggingInterceptor class responsible for logging gRPC requests and responses.
 */
@Component
public class InfrastructureLoggingInterceptor implements ServerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(InfrastructureLoggingInterceptor.class);

    /**
     * Intercepts incoming gRPC calls to log requests and responses.
     *
     * @param call    The server call object.
     * @param headers The metadata headers.
     * @param next    The server call handler.
     * @return The listener for server call events.
     */
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {

        // Log request method and headers
        logger.info("gRPC Request - Method: {}, Headers: {}", call.getMethodDescriptor().getFullMethodName(), headers);

        // Wrap the ServerCall to log responses
        ServerCall<ReqT, RespT> loggingServerCall = new ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT>(call) {
            @Override
            public void sendMessage(RespT message) {
                // Log response message
                logger.info("gRPC Response - Method: {}, Message: {}", call.getMethodDescriptor().getFullMethodName(), message);
                super.sendMessage(message);
            }
        };

        return next.startCall(loggingServerCall, headers);
    }
}