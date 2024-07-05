package tel_location_item_bot.bot;

import reactor.core.publisher.Mono;
import tel_location_item_bot.house.House;
import tel_location_item_bot.house.HouseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            return Mono.just("Вітаю! Це ваш Telegram бот для управлінням предметів");
        }

        if (message.startsWith("/house/edit/")) {

            String[] parts = message.substring("/house/edit/".length()).split(";");

            if (parts.length != 3) {
                return Mono.just("Невірний формат команди. Використовуйте: /house/edit/[id];[name];[address]");
            }

            try {
                Long id = Long.parseLong(parts[0].trim());
                String name = parts[1].trim();
                String address = parts[2].trim();

                House editedHouse = new House();
                editedHouse.setId(id);
                editedHouse.setName(name);
                editedHouse.setAddress(address);

                return houseService.editHouseById(editedHouse, id)
                        .map(updatedHouse -> "Будинок з ID " + updatedHouse.getId() + " успішно відредаговано.")
                        .defaultIfEmpty("Не вдалося знайти будинок для редагування.");
            } catch (NumberFormatException e) {
                return Mono.just("Некоректний формат ID.");
            }
        }

        if (message.startsWith("/house/create ")) {
            String[] parts = message.substring(14).split(";");
            if (parts.length != 2) {
                return Mono.just("Невірний формат команди. Використовуйте: /house/create [name];[address]");
            }

            House newHouse = new House();
            newHouse.setName(parts[0].trim());
            newHouse.setAddress(parts[1].trim());

            return houseService.createHouse(newHouse)
                    .map(House::toString)
                    .defaultIfEmpty("Не вдалося створити будинок.");
        }

        if (message.startsWith("/house/list")) {
            return houseService.getAllHouses()
                    .map(houses -> houses.stream()
                            .map(House::toString)
                            .collect(Collectors.joining("\n")));
        }

        if (message.startsWith("/house/")) {
            try {
                Long id = Long.parseLong(message.substring(7).trim());
                return houseService.getHouseById(id)
                        .map(House::toString)
                        .defaultIfEmpty("Будинок не знайдено");
            } catch (NumberFormatException e) {
                return Mono.just("Некоректний формат ID.");
            }
        }

        return Mono.just("Невідома команда.");
    }
}
