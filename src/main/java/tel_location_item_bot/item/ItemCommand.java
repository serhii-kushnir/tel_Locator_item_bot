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

    public Mono<String> getById(final String message) {
        String[] parts = message.substring("/item ".length()).split(";");
        if (parts.length != 1) {
            return Mono.just("Невірний формат команди. Використовуйте: /item [id]");
        }

        try {
            Long id = Long.parseLong(parts[0].trim());

            return itemService.getItemById(id)
                    .map(Item::toString)
                    .defaultIfEmpty("Предмет з ID " + id + " не знайдено.");
        } catch (NumberFormatException e) {
            return Mono.just("Некоректний /item ID.");
        }
    }

    public Mono<String> create(final String message) {
        String[] parts = message.substring("/item/create ".length()).split(";");
        if (parts.length != 5) {
            return Mono.just("Невірний формат команди. Використовуйте: /item/create [name];[description];[quantity];[room id];[box id]");
        }

        long roomId;
        long boxId;
        int quantity;

        try {
            roomId = Long.parseLong(parts[3].trim());
            boxId = Long.parseLong(parts[4].trim());
            quantity = Integer.parseInt(parts[2].trim());
        } catch (NumberFormatException e) {
            return Mono.just("Невірний формат ID кімнати або ID коробки або кількості.");
        }

        ItemDTO newItemDTO = new ItemDTO();
        newItemDTO.setName(parts[0].trim());
        newItemDTO.setDescription(parts[1].trim());
        newItemDTO.setQuantity(quantity);
        newItemDTO.setRoomId(roomId);
        newItemDTO.setBoxId(boxId);

        return itemService.createItem(newItemDTO)
                .map(item -> "Предмет створений: " + item.toString())
                .defaultIfEmpty("Не вдалося створити предмет.");
    }

    public Mono<String> edit(final String message) {
        String[] parts = message.substring("/item/edit ".length()).split(";");
        if (parts.length != 6) {
            return Mono.just("Невірний формат команди. Використовуйте: /item/edit [id];[name];[description];[quantity];[room id];[box id]");
        }

        long id;
        long roomId;
        long boxId;
        int quantity;

        try {
            id = Long.parseLong(parts[0].trim());
            roomId = Long.parseLong(parts[4].trim());
            boxId = Long.parseLong(parts[5].trim());
            quantity = Integer.parseInt(parts[3].trim());
        } catch (NumberFormatException e) {
            return Mono.just("Невірний формат ID предмета, ID кімнати, ID коробки або кількості.");
        }

        ItemDTO editdItemDTO = new ItemDTO();
        editdItemDTO.setId(id);
        editdItemDTO.setName(parts[1].trim());
        editdItemDTO.setDescription(parts[2].trim());
        editdItemDTO.setQuantity(quantity);
        editdItemDTO.setRoomId(roomId);
        editdItemDTO.setBoxId(boxId);

        return itemService.editItemById(editdItemDTO, id)
                .map(item -> "Предмет оновлений: " + item.toString())
                .defaultIfEmpty("Не вдалося оновити предмет.");
    }
}
