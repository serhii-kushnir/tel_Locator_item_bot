package tel_location_item_bot.box;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class BoxCommand {

    private final BoxService boxService;

    @Autowired
    public BoxCommand(BoxService boxService) {
        this.boxService = boxService;
    }

    public Mono<String> create(final String message) {
        String[] parts = message.substring("/box/create ".length()).split(";");
        if (parts.length != 2) {
            return Mono.just("Невірний формат команди. Використовуйте: /box/create [name];[room id]");
        }

        long roomId;
        try {
            roomId = Long.parseLong(parts[1].trim());
        } catch (NumberFormatException e) {
            return Mono.just("Невірний формат ID кімнати.");
        }

        BoxDTO newBoxDTO = new BoxDTO();
        newBoxDTO.setName(parts[0].trim());
        newBoxDTO.setRoomId(roomId);

        return boxService.createBox(newBoxDTO)
                .map(box -> "Комірка створена: " + box.toString())
                .defaultIfEmpty("Не вдалося створити комірку.");
    }

}
