package tel_location_item_bot.auth;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public final class AuthService {

    private AuthConfig authConfig;
    private WebClient webClient;

    public void loginAndSetJwtToken() {
        Mono<AuthResponse> authResponseMono = webClient.post()
                .uri("/auth/login")
                .body(BodyInserters.fromValue(new AuthLoginRequest("Serhii 2", "pass")))
                .retrieve()
                .bodyToMono(AuthResponse.class);

        authResponseMono.subscribe(authResponse -> authConfig.setJwtToken(authResponse.getToken()), Throwable::printStackTrace);
    }

}
