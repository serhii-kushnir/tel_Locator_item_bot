package tel_location_item_bot.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Component
public class RoomCommand {

    private final RoomService roomService;

    @Autowired
    public RoomCommand(final RoomService roomService) {
        this.roomService = roomService;
    }

    public Mono<String> getListRooms() {
        return roomService.getListRooms()
                .map(rooms -> rooms.stream()
                        .map(Room::toString)
                        .collect(Collectors.joining("\n")));
    }

    public Mono<String> getById(final String message) {
        String[] parts = message.substring("/room".length()).split(";");
        if (parts.length != 1) {
            return Mono.just("Невірний формат команди. Використовуйте: /house [id]");
        }

        try {
            Long id = Long.parseLong(parts[0].trim());

            return roomService.getRoomById(id)
                    .map(Room::toString)
                    .defaultIfEmpty("Кімната з ID " + id + " не знайдена.");
        } catch (NumberFormatException e) {
            return Mono.just("Некоректний формат ID.");
        }
    }
}
