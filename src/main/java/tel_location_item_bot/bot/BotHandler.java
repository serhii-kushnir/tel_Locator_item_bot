package tel_location_item_bot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

import tel_location_item_bot.auth.AuthHandler;
import tel_location_item_bot.auth.AuthService;
import tel_location_item_bot.cell.CellHandler;
import tel_location_item_bot.house.HouseHandler;
import tel_location_item_bot.item.ItemHandler;
import tel_location_item_bot.room.RoomHandler;

@Service
public final class BotHandler {

    private final HouseHandler houseHandler;
    private final RoomHandler roomHandler;
    private final ItemHandler itemHandler;
    private final CellHandler cellHandler;
    private final AuthHandler authHandler;
    private final AuthService authService;

    @Autowired
    public BotHandler(final HouseHandler houseHandler,
                      final RoomHandler roomHandler,
                      final ItemHandler itemHandler,
                      final CellHandler cellHandler,
                      final AuthHandler authHandler,
                      final AuthService authService) {
        this.houseHandler = houseHandler;
        this.roomHandler = roomHandler;
        this.itemHandler = itemHandler;
        this.cellHandler = cellHandler;
        this.authService = authService;
        this.authHandler = authHandler;

        loginAndSetJwtToken();
    }

    public Mono<String> processMessage(final String messageText) {
        if (messageText.equals("/start")) {
            return Mono.just("Вітаю! Це ваш Telegram бот для управлінням предметів");
        }

        if (messageText.startsWith("/register")) {
            return authHandler.handler(messageText);
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
        authService.loginAndSetJwtToken();
    }
}
