package tel_location_item_bot.bot.command.room;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class BotCommandRoomHandler {

    private final BotCommandRoom botCommandRoom;

    @Autowired
    public BotCommandRoomHandler(final BotCommandRoom botCommandRoom) {
        this.botCommandRoom = botCommandRoom;
    }

    public Mono<String> handler(final String messageText) {
        String[] parts = messageText.split(" ", 2);
        String command = parts[0];
//        String arguments = parts.length > 1 ? parts[1] : "";

        return switch (command) {
            case "/room/list" -> botCommandRoom.getListRooms();
//            case "/room/create" -> botCommandRoom.create("/room/create " + arguments);
//            case "/house" -> botCommandRoom.getById("/house " + arguments);
//            case "/room/edit" -> botCommandRoom.edit("/room/edit " + arguments);
//            case "/room/delete" -> botCommandRoom.delete("/room/delete " + arguments);
            default -> Mono.just("Невідома команда для /room");
        };
    }
}
