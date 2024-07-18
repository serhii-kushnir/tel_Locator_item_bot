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
                .body(BodyInserters.fromValue(new AuthLoginRequest("user1", "pass")))
                .retrieve()
                .bodyToMono(AuthResponse.class);

        authResponseMono.subscribe(authResponse -> authConfig.setJwtToken(authResponse.getToken()), Throwable::printStackTrace);
    }

    public Mono<String> register(final String username, final String email, final String password) {
        return webClient.post()
                .uri("/auth/register")
                .body(BodyInserters.fromValue(new AuthRegisterRequest(username, email, password)))
                .retrieve()
                .bodyToMono(AuthResponse.class)
                .map(authResponse -> {
                    authConfig.setJwtToken(authResponse.getToken());
                    return "Реєстрація успішна: користуваяч " + username;
                });
    }
}
