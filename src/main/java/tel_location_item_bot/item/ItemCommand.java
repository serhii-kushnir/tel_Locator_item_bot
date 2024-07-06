package tel_location_item_bot.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import tel_location_item_bot.box.Box;
import tel_location_item_bot.box.BoxService;
import tel_location_item_bot.room.Room;
import tel_location_item_bot.room.RoomService;

import java.util.stream.Collectors;


@Component
public class ItemCommand {

    private final ItemService itemService;
    private final RoomService roomService;
    private final BoxService boxService;

    @Autowired
    public ItemCommand(final ItemService itemService,
                       final RoomService roomService,
                       final BoxService boxService) {
        this.itemService = itemService;
        this.roomService = roomService;
        this.boxService = boxService;
    }

    public Mono<String> getList() {
        return itemService.getListItems()
                .flatMapMany(Flux::fromIterable)
                .flatMap(item -> {
                    Mono<Room> roomMono = item.getRoomId() != null ?
                            roomService.getRoomById(item.getRoomId()) :
                            Mono.just(null);

                    Mono<Box> boxMono = item.getBoxId() != null ?
                            boxService.getBoxById(item.getBoxId()) :
                            Mono.just(null);

                    return Mono.zip(roomMono, boxMono, (room, box) -> {
                        item.setRoom(room);
                        item.setBox(box);
                        return item;
                    });
                })
                .collectList()
                .map(items -> items.stream()
                        .map(Item::toString)
                        .collect(Collectors.joining("\n")));
    }

}
