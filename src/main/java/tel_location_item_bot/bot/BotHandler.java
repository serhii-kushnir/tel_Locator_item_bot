package tel_location_item_bot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;
import tel_location_item_bot.house.HouseHandler;
import tel_location_item_bot.room.RoomHandler;

@Service
public class BotHandler {

    private final HouseHandler houseHandler;
    private final RoomHandler roomHandler;

    @Autowired
    public BotHandler(final HouseHandler houseHandler, final RoomHandler roomHandler) {
        this.houseHandler = houseHandler;
        this.roomHandler = roomHandler;
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

        return Mono.just("Невідома команда");
    }


}
