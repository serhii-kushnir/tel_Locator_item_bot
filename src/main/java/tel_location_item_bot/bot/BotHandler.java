package tel_location_item_bot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import tel_location_item_bot.cell.CellHandler;
import tel_location_item_bot.config.AuthLoginRequest;
import tel_location_item_bot.config.AuthResponse;
import tel_location_item_bot.config.WebClientConfig;
import tel_location_item_bot.house.HouseHandler;
import tel_location_item_bot.item.ItemHandler;
import tel_location_item_bot.room.RoomHandler;

@Service
public class BotHandler {

    private final HouseHandler houseHandler;
    private final RoomHandler roomHandler;
    private final ItemHandler itemHandler;
    private final CellHandler cellHandler;
    private final WebClientConfig webClientConfig;
    private final WebClient webClient;

    @Autowired
    public BotHandler(final HouseHandler houseHandler,
                      final RoomHandler roomHandler,
                      final ItemHandler itemHandler,
                      final CellHandler cellHandler,
                      final WebClientConfig webClientConfig,
                      final WebClient webClient) {
        this.houseHandler = houseHandler;
        this.roomHandler = roomHandler;
        this.itemHandler = itemHandler;
        this.cellHandler = cellHandler;
        this.webClientConfig = webClientConfig;
        this.webClient = webClient;

        loginAndSetJwtToken();
    }

    public Mono<String> processMessage(final String messageText) {
        if (messageText.equals("/start")) {
            return Mono.just("Вітаю! Це ваш Telegram бот для управлінням предметів");
        }

        if (messageText.startsWith("/house")) {
            return houseHandler.handler(messageText);
        }

        if (messageText.startsWith("/room")) {
            return roomHandler.handler(messageText);
        }

        if (messageText.startsWith("/cell")) {
            return cellHandler.handler(messageText);
        }

        if (messageText.startsWith("/item")) {
            return itemHandler.handler(messageText);
        }

        return Mono.just("Невідома команда");
    }

    private void loginAndSetJwtToken() {
        Mono<AuthResponse> authResponseMono = webClient.post()
                .uri("/auth/login")
                .body(BodyInserters.fromValue(new AuthLoginRequest("Serhii 2", "pass")))
                .retrieve()
                .bodyToMono(AuthResponse.class);

        authResponseMono.subscribe(authResponse -> webClientConfig.setJwtToken(authResponse.getToken()), Throwable::printStackTrace);
    }
}
