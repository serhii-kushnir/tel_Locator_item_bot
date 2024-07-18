package tel_location_item_bot.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class AuthHandler {

    private final AuthCommand authCommand;

    @Autowired
    public AuthHandler(final AuthCommand authCommand) {
        this.authCommand = authCommand;
    }

    public Mono<String> handler(final String messageText) {
        String[] parts = messageText.split(" ", 2);
        String command = parts[0];
        String arguments = parts[1];

        return getCommandAuth(command, arguments);
    }

    private Mono<String> getCommandAuth(String command, String arguments) {
        return switch (command) {
//            case "/login" -> authCommand.login(arguments);
            case "/register" -> authCommand.register("/register " + arguments);
            default -> Mono.just("Невідома команда для /auth");
        };
    }
}
