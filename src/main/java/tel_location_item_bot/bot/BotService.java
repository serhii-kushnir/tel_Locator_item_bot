package tel_location_item_bot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;
import tel_location_item_bot.bot.command.house.BotCommandHouseHandler;
import tel_location_item_bot.bot.command.room.BotCommandRoomHandler;

@Service
public class BotService {

    private final BotCommandHouseHandler botCommandHouseHandler;
    private final BotCommandRoomHandler botCommandRoomHandler;

    @Autowired
    public BotService(final BotCommandHouseHandler botCommandHouseHandler, final BotCommandRoomHandler botCommandRoomHandler) {
        this.botCommandHouseHandler = botCommandHouseHandler;
        this.botCommandRoomHandler = botCommandRoomHandler;
    }

    public Mono<String> processMessage(final String messageText) {
        if (messageText.equals("/start")) {
            return Mono.just("Вітаю! Це ваш Telegram бот для управлінням предметів");
        }

        if (messageText.startsWith("/house")) {
            return botCommandHouseHandler.handler(messageText);
        }

        if (messageText.startsWith("/room")) {
            return botCommandRoomHandler.handler(messageText);
        }

        return Mono.just("Невідома команда");
    }


}
