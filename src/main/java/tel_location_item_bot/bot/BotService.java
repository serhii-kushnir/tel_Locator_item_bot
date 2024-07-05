package tel_location_item_bot.bot;

import reactor.core.publisher.Mono;
import tel_location_item_bot.house.House;
import tel_location_item_bot.house.HouseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BotService {

    private final HouseService houseService;

    @Autowired
    public BotService(HouseService houseService) {
        this.houseService = houseService;
    }

    public Mono<String> processMessage(String message) {
        if (message.equals("/start")) {
            return Mono.just("Вітаю! Це ваш Telegram бот для показу розташування предметів");
        }

        if (message.startsWith("/house")) {
            return houseService.getAllHouses()
                    .map(houses -> houses.stream()
                            .map(House::toString)
                            .collect(Collectors.joining("\n")));
        }

        return Mono.just("Невідома команда.");
    }
}
