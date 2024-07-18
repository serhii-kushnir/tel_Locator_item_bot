package tel_location_item_bot.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class AuthCommand {

    private final AuthService authService;

    @Autowired
    public AuthCommand(AuthService authService) {
        this.authService = authService;
    }

//    public Mono<String> login(final String message) {
//        String[] parts = message.substring("/login ".length()).split(";");
//
//        if (parts.length != 2) {
//            return Mono.just("Невірний формат команди. Використовуйте: /login [username];[password]");
//        }
//
//        String username = parts[0];
//        String password = parts[1];
//
//        authService.loginAndSetJwtToken(username, password);
//
//        return Mono.just("Ви увійшли як " + username);
//    }

    public Mono<String> register(final String message) {
        String[] parts = message.substring("/register ".length()).split(";");

        if (parts.length != 3) {
            return Mono.just("Невірний формат команди. Використовуйте: /register [username];[email];[password]");
        }

        String username = parts[0];
        String email = parts[1];
        String password = parts[2];

        return authService.register(username, email, password)
                .onErrorReturn("Помилка реєстрації. Спробуйте ще раз.");
    }

}
