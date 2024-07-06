package tel_location_item_bot.box;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

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

    public Mono<String> getList() {
        return boxService.getListBoxes()
                .map(boxes -> boxes.stream()
                        .map(Box::toString)
                        .collect(Collectors.joining("\n")));
    }

    public Mono<String> getById(final String message) {
        String[] parts = message.substring("/box".length()).split(";");
        if (parts.length != 1) {
            return Mono.just("Невірний формат команди. Використовуйте: /box [id]");
        }

        try {
            Long id = Long.parseLong(parts[0].trim());

            return boxService.getBoxById(id)
                    .map(Box::toString)
                    .defaultIfEmpty("Комірка з ID " + id + " не знайдена.");
        } catch (NumberFormatException e) {
            return Mono.just("Некоректний формат ID.");
        }
    }

    public Mono<String> edit(final String message) {
        String[] parts = message.substring("/box/edit ".length()).split(";");
        if (parts.length != 3) {
            return Mono.just("Невірний формат команди. Використовуйте: /box/edit [id];[name];[room id]");
        }

        Long boxId;
        try {
            boxId = Long.parseLong(parts[0].trim());
        } catch (NumberFormatException e) {
            return Mono.just("Невірний формат ID боксу.");
        }

        long roomId;
        try {
            roomId = Long.parseLong(parts[2].trim());
        } catch (NumberFormatException e) {
            return Mono.just("Невірний формат ID кімнати.");
        }

        BoxDTO editedBoxDTO = new BoxDTO();
        editedBoxDTO.setId(boxId);
        editedBoxDTO.setName(parts[1].trim());
        editedBoxDTO.setRoomId(roomId);

        return boxService.editBoxById(editedBoxDTO, boxId)
                .map(box -> "Комірка з ID " + box.getId() + " відредагована: " + box.toString())
                .defaultIfEmpty("Не вдалося знайти комірку для редагування.");
    }

}
