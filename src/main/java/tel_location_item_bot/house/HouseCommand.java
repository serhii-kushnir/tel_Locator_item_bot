package tel_location_item_bot.house;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Component
public final class HouseCommand {

    private static final String HOUSE_ID = "Будинок з ID ";
    private static final String INCORRECT_ID = "Некоректний формат ID.";

    private final HouseService houseService;

    @Autowired
    public HouseCommand(HouseService houseService) {
        this.houseService = houseService;
    }

    public Mono<String> create(final String message) {
        String[] parts = message.substring("/house/create ".length()).split(";");
        if (parts.length != 2) {
            return Mono.just("Невірний формат команди. Використовуйте: /house/create [name];[address]");
        }
        
        House newHouse = House.builder()
                .name(parts[0].trim())
                .address(parts[1].trim())
                .build();

        return houseService.createHouse(newHouse)
                .map(house -> "Дім створено: " + house.toString())
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

            House editedHouse = House.builder()
                    .id(id)
                    .name(name)
                    .address(address)
                    .build();


            return houseService.editHouseById(editedHouse, id)
                    .map(updatedHouse -> HOUSE_ID + updatedHouse.getId() + " успішно відредаговано.")
                    .defaultIfEmpty("Не вдалося знайти будинок для редагування.");
        } catch (NumberFormatException e) {
            return Mono.just(INCORRECT_ID);
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
                    .then(Mono.just(HOUSE_ID + id + " успішно видалено."));
        } catch (NumberFormatException e) {
            return Mono.just(INCORRECT_ID);
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
                    .defaultIfEmpty(HOUSE_ID + id + " не знайдено.");
        } catch (NumberFormatException e) {
            return Mono.just(INCORRECT_ID);
        }
    }

    public Mono<String> getListHouse() {
        return houseService.getListHouses()
                    .map(houses -> houses.stream()
                            .map(House::toString)
                            .collect(Collectors.joining("\n")));
    }
}
