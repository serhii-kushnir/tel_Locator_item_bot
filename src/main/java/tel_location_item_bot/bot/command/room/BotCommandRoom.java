package tel_location_item_bot.bot.command.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import tel_location_item_bot.room.Room;
import tel_location_item_bot.room.RoomService;

import java.util.stream.Collectors;

@Component
public class BotCommandRoom {

    private final RoomService roomService;

    @Autowired
    public BotCommandRoom(final RoomService roomService) {
        this.roomService = roomService;
    }

    public Mono<String> getListRooms() {
        return roomService.getListRooms()
                .map(rooms -> rooms.stream()
                        .map(Room::toString)
                        .collect(Collectors.joining("\n")));
    }

}
