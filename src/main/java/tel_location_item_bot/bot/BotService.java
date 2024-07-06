package tel_location_item_bot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;
import tel_location_item_bot.bot.command.BotCommandHouseHandler;

@Service
public class BotService {

    private final BotCommandHouseHandler botCommandHouseHandler;

    @Autowired
    public BotService(BotCommandHouseHandler botCommandHouseHandler) {
        this.botCommandHouseHandler = botCommandHouseHandler;
    }

    public Mono<String> processMessage(String messageText) {
        if (messageText.equals("/start")) {
            return Mono.just("Вітаю! Це ваш Telegram бот для управлінням предметів");
        }

        if (messageText.startsWith("/house")) {
            return botCommandHouseHandler.handler(messageText);
        }

        return Mono.just("Невідома команда.");
    }


}
