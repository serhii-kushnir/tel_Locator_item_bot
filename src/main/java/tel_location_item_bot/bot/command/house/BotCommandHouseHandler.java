package tel_location_item_bot.bot.command.house;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class BotCommandHouseHandler {

    private final BotCommandHouse botCommandHouse;

    @Autowired
    public BotCommandHouseHandler(final BotCommandHouse botCommandHouse) {
        this.botCommandHouse = botCommandHouse;
    }

    public Mono<String> handler(final String messageText) {
        String[] parts = messageText.split(" ", 2);
        String command = parts[0];
        String arguments = parts.length > 1 ? parts[1] : "";

        return switch (command) {
            case "/house/list" -> botCommandHouse.getListHouse();
            case "/house/create" -> botCommandHouse.create("/house/create " + arguments);
            case "/house" -> botCommandHouse.getById("/house " + arguments);
            case "/house/edit" -> botCommandHouse.edit("/house/edit " + arguments);
            case "/house/delete" -> botCommandHouse.delete("/house/delete " + arguments);
            default -> Mono.just("Невідома команда для /house");
        };
    }
}
