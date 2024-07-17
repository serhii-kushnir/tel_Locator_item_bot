package tel_location_item_bot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    private String jwtToken;

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:9998/api/v1")
                .filter(addJwtTokenHeader())
                .build();
    }

    private ExchangeFilterFunction addJwtTokenHeader() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            if (jwtToken != null && !jwtToken.isEmpty()) {
                return Mono.just(ClientRequest.from(clientRequest)
                        .header("Authorization", "Bearer " + jwtToken)
                        .build());
            } else {
                return Mono.just(clientRequest);
            }
        });
    }
}
