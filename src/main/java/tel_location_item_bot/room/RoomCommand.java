package tel_location_item_bot.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Component
public class RoomCommand {

    private final RoomService roomService;

    @Autowired
    public RoomCommand(RoomService roomService) {
        this.roomService = roomService;
    }

    public Mono<String> getList() {
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

    public Mono<String> create(final String message) {
        String[] parts = message.substring("/room/create ".length()).split(";");
        if (parts.length != 2) {
            return Mono.just("Невірний формат команди. Використовуйте: /room/create [name];[house id]");
        }

        long houseId;
        try {
            houseId = Long.parseLong(parts[1].trim());
        } catch (NumberFormatException e) {
            return Mono.just("Невірний формат ID дому.");
        }

        RoomDTO newRoomDTO = new RoomDTO();
        newRoomDTO.setName(parts[0].trim());
        newRoomDTO.setHouseId(houseId);

        return roomService.createRoom(newRoomDTO)
                .map(room -> "Кімната створена: " + room.toString())
                .defaultIfEmpty("Не вдалося створити кімнату.");
    }

    public Mono<String> edit(final String message) {
        String[] parts = message.substring("/room/edit ".length()).split(";");
        if (parts.length != 3) {
            return Mono.just("Невірний формат команди. Використовуйте: /room/edit [id];[name];[house id]");
        }

        Long roomId;
        try {
            roomId = Long.parseLong(parts[0].trim());
        } catch (NumberFormatException e) {
            return Mono.just("Невірний формат ID кімнати.");
        }

        long houseId;
        try {
            houseId = Long.parseLong(parts[2].trim());
        } catch (NumberFormatException e) {
            return Mono.just("Невірний формат ID дому.");
        }

        RoomDTO editedRoomDTO = new RoomDTO();
        editedRoomDTO.setId(roomId);
        editedRoomDTO.setName(parts[1].trim());
        editedRoomDTO.setHouseId(houseId);

        return roomService.editRoomById(editedRoomDTO, roomId)
                .map(room -> "Кімната з ID " + room.getId() + " відредагована: " + room.toString())
                .defaultIfEmpty("Не вдалося знайти кімнату для редагування.");
    }

    public Mono<String> delete(final String message) {
        String roomIdStr = message.substring("/room/delete ".length()).trim();

        long roomId;
        try {
            roomId = Long.parseLong(roomIdStr);
        } catch (NumberFormatException e) {
            return Mono.just("Невірний формат ID кімнати для видалення.");
        }

        return roomService.deleteRoomById(roomId)
                .then(Mono.just("Кімната з ID " + roomId + " видалена."));
    }
}
