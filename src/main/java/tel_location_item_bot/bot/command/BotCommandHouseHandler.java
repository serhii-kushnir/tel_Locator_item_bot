package tel_location_item_bot.bot.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

import tel_location_item_bot.house.House;
import tel_location_item_bot.house.HouseService;

import java.util.stream.Collectors;

@Component
public class BotCommandHouseHandler {

    private final HouseService houseService;
    private final BotCommandHouse botCommandHouse;

    @Autowired
    public BotCommandHouseHandler(HouseService houseService, BotCommandHouse botCommandHouse) {
        this.houseService = houseService;
        this.botCommandHouse = botCommandHouse;
    }

    public Mono<String> handler(String messageText) {
        String[] parts = messageText.split(" ", 2);
        String command = parts[0];
        String arguments = parts.length > 1 ? parts[1] : "";

        return switch (command) {
            case "/house/list" -> houseService.getAllHouses()
                    .map(houses -> houses.stream()
                            .map(House::toString)
                            .collect(Collectors.joining("\n")));

            case "/house/create" -> botCommandHouse.create("/house/create " + arguments);
            case "/house" -> botCommandHouse.getById("/house " + arguments);
            case "/house/edit" -> botCommandHouse.edit("/house/edit " + arguments);
            case "/house/delete" -> botCommandHouse.delete("/house/delete " + arguments);
            default -> Mono.just("Невідома команда для /house.");
        };
    }
}
