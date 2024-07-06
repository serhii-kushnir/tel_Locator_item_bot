package tel_location_item_bot.bot.command.house;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

import tel_location_item_bot.house.House;
import tel_location_item_bot.house.HouseService;

import java.util.stream.Collectors;

@Component
public class BotCommandHouse {

    private final HouseService houseService;

    @Autowired
    public BotCommandHouse(HouseService houseService) {
        this.houseService = houseService;
    }

    public Mono<String> create(final String message) {
        String[] parts = message.substring("/house/create ".length()).split(";");
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

    public Mono<String> edit(final String message) {
        String[] parts = message.substring("/house/edit ".length()).split(";");
        if (parts.length != 3) {
            return Mono.just("Невірний формат команди. Використовуйте: /house/edit [id];[name];[address]");
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

    public Mono<String> delete(final String message) {
        String[] parts = message.substring("/house/delete ".length()).split(";");

        if (parts.length != 1) {
            return Mono.just("Невірний формат команди. Використовуйте: /house/delete [id]");
        }

        try {
            Long id = Long.parseLong(parts[0].trim());

            return houseService.deleteHouseById(id)
                    .then(Mono.just("Будинок з ID " + id + " успішно видалено."));
        } catch (NumberFormatException e) {
            return Mono.just("Некоректний формат ID.");
        }
    }


    public Mono<String> getById(final String message) {
        String[] parts = message.substring("/house".length()).split(";");
        if (parts.length != 1) {
            return Mono.just("Невірний формат команди. Використовуйте: /house [id]");
        }

        try {
            Long id = Long.parseLong(parts[0].trim());

            return houseService.getHouseById(id)
                    .map(House::toString)
                    .defaultIfEmpty("Будинок з ID " + id + " не знайдено.");
        } catch (NumberFormatException e) {
            return Mono.just("Некоректний формат ID.");
        }
    }

    public Mono<String> getListHouse() {
        return houseService.getListHouses()
                    .map(houses -> houses.stream()
                            .map(House::toString)
                            .collect(Collectors.joining("\n")));
    }
}
