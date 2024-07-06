package tel_location_item_bot.room;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static tel_location_item_bot.utility.Constant.PREFIX_ROOM;

@Component
public class RoomHandler {

    private final RoomCommand roomCommand;

    @Autowired
    public RoomHandler(final RoomCommand roomCommand) {
        this.roomCommand = roomCommand;
    }

    public Mono<String> handler(final String messageText) {
        String[] parts = messageText.split(" ", 2);
        String command = parts[0];
        String arguments = parts.length > 1 ? parts[1] : "";

        return switch (command) {
            case PREFIX_ROOM + "list" -> roomCommand.getListRooms();
            case PREFIX_ROOM + "create" -> roomCommand.create("/room/create " + arguments);
            case "/room" -> roomCommand.getById("/room " + arguments);
//            case "/room/edit" -> roomCommand.edit("/room/edit " + arguments);
//            case "/room/delete" -> roomCommand.delete("/room/delete " + arguments);
            default -> Mono.just("Невідома команда для /room");
        };
    }
}
