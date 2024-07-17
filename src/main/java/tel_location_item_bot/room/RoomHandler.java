package tel_location_item_bot.room;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class RoomHandler {

    public static final String PREFIX_ROOM = "/room";
    private final RoomCommand roomCommand;

    @Autowired
    public RoomHandler(final RoomCommand roomCommand) {
        this.roomCommand = roomCommand;
    }

    public Mono<String> handler(final String messageText) {
        String[] parts = messageText.split(" ", 2);
        String command = parts[0];
        String arguments = parts.length > 1 ? parts[1] : "";

        return getCommandRoom(command, arguments);
    }

    private Mono<String> getCommandRoom(String command, String arguments) {
        return switch (command) {
            case PREFIX_ROOM + "/list" -> roomCommand.getList();
            case PREFIX_ROOM + "/create" -> roomCommand.create(PREFIX_ROOM + "/create " + arguments);
            case PREFIX_ROOM -> roomCommand.getById(PREFIX_ROOM + " " + arguments);
            case PREFIX_ROOM + "/edit" -> roomCommand.edit(PREFIX_ROOM + "/edit " + arguments);
            case PREFIX_ROOM + "/delete" -> roomCommand.delete(PREFIX_ROOM + "/delete " + arguments);
            default -> Mono.just("Невідома команда для " + PREFIX_ROOM);
        };
    }
}
