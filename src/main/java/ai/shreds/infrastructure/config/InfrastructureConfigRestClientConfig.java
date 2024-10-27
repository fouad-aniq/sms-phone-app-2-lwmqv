package ai.shreds.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

@Configuration
public class InfrastructureConfigRestClientConfig {

    public void restClientConfig() {
        // Additional configurations can be added here if necessary.
        // This method corresponds to the method defined in the UML and returns void.
    }

    @Bean
    public RestTemplate restTemplate() {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        requestFactory.setConnectTimeout(5000);
        requestFactory.setReadTimeout(5000);
        requestFactory.setConnectionRequestTimeout(5000);

        RestTemplate restTemplate = new RestTemplate(requestFactory);

        // Additional configuration for the RestTemplate can be done here
        // e.g., adding message converters, error handlers, interceptors, etc.

        return restTemplate;
    }
}