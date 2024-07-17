package tel_location_item_bot.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

import tel_location_item_bot.cell.Cell;
import tel_location_item_bot.cell.CellDTO;
import tel_location_item_bot.cell.CellService;
import tel_location_item_bot.room.Room;
import tel_location_item_bot.room.RoomDTO;
import tel_location_item_bot.room.RoomService;

import java.util.stream.Collectors;

@Component
public class ItemCommand {

    private final ItemService itemService;
    private final RoomService roomService;
    private final CellService cellService;

    @Autowired
    public ItemCommand(final ItemService itemService,
                       final RoomService roomService,
                       final CellService cellService) {
        this.itemService = itemService;
        this.roomService = roomService;
        this.cellService = cellService;
    }

    public Mono<String> getList() {
        return itemService.getListItems()
                .flatMapIterable(items -> items)
                .map(Item::toString)
                .collect(Collectors.joining("\n"))
                .switchIfEmpty(Mono.just("No items found"));
    }

    public Mono<String> getById(final String message) {
        String[] parts = message.substring("/item ".length()).split(";");
        if (parts.length != 1) {
            return Mono.just("Невірний формат команди. Використовуйте: /item [id]");
        }

        try {
            Long id = Long.parseLong(parts[0].trim());

            return itemService.getItemById(id)
                    .flatMap(item -> {
                        Mono<Room> roomMono;
                        Mono<Cell> cellMono;

                        if (item.getRoom() != null) {
                            roomMono = roomService.getRoomById(item.getRoom().getId());
                        } else {
                            roomMono = Mono.empty();
                        }

                        if (item.getCell() != null) {
                            cellMono = cellService.getCellById(item.getCell().getId());
                        } else {
                            cellMono = Mono.empty();
                        }

                        return Mono.zip(roomMono.defaultIfEmpty(new Room()), cellMono.defaultIfEmpty(new Cell()), (room, cell) -> {
                            item.setRoom(room);
                            item.setCell(cell);
                            return item.toStringById();
                        });
                    })
                    .defaultIfEmpty("Предмет з ID " + id + " не знайдено.");
        } catch (NumberFormatException e) {
            return Mono.just("Некоректний /item ID.");
        }
    }


    public Mono<String> create(final String message) {
        String[] parts = message.substring("/item/create ".length()).split(";");
        if (parts.length != 5) {
            return Mono.just("Невірний формат команди. Використовуйте: /item/create [name];[description];[quantity];[room id];[cell id]");
        }

        long roomId;
        long cellId;
        int quantity;

        try {
            roomId = Long.parseLong(parts[3].trim());
            cellId = Long.parseLong(parts[4].trim());
            quantity = Integer.parseInt(parts[2].trim());
        } catch (NumberFormatException e) {
            return Mono.just("Невірний формат ID кімнати або ID коробки або кількості.");
        }

        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(roomId);

        CellDTO cellDTO = new CellDTO();
        cellDTO.setId(cellId);

        ItemDTO newItemDTO = new ItemDTO();
        newItemDTO.setName(parts[0].trim());
        newItemDTO.setDescription(parts[1].trim());
        newItemDTO.setQuantity(quantity);
        newItemDTO.setRoom(roomDTO);
        newItemDTO.setCell(cellDTO);

        return itemService.createItem(newItemDTO)
                .map(item -> "Предмет створений: " + item.toString())
                .defaultIfEmpty("Не вдалося створити предмет.");
    }

    public Mono<String> edit(final String message) {
        String[] parts = message.substring("/item/edit ".length()).split(";");
        if (parts.length != 6) {
            return Mono.just("Невірний формат команди. Використовуйте: /item/edit [id];[name];[description];[quantity];[room id];[cell id]");
        }

        long id;
        long roomId;
        long cellId;
        int quantity;

        try {
            id = Long.parseLong(parts[0].trim());
            roomId = Long.parseLong(parts[4].trim());
            cellId = Long.parseLong(parts[5].trim());
            quantity = Integer.parseInt(parts[3].trim());
        } catch (NumberFormatException e) {
            return Mono.just("Невірний формат ID предмета, ID кімнати, ID коробки або кількості.");
        }

        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(roomId);

        CellDTO cellDTO = new CellDTO();
        cellDTO.setId(cellId);

        ItemDTO editedItemDTO = new ItemDTO();
        editedItemDTO.setId(id);
        editedItemDTO.setName(parts[1].trim());
        editedItemDTO.setDescription(parts[2].trim());
        editedItemDTO.setQuantity(quantity);
        editedItemDTO.setRoom(roomDTO);
        editedItemDTO.setCell(cellDTO);

        return itemService.editItemById(editedItemDTO, id)
                .map(item -> "Предмет оновлений: " + item.toString())
                .defaultIfEmpty("Не вдалося оновити предмет.");
    }

    public Mono<String> delete(final String message) {
        String[] parts = message.split(" ");
        if (parts.length != 2) {
            return Mono.just("Невірний формат команди. Використовуйте: /item/delete [id]");
        }

        long id;
        try {
            id = Long.parseLong(parts[1].trim());
        } catch (NumberFormatException e) {
            return Mono.just("Невірний формат ID предмета.");
        }

        return itemService.deleteItemById(id)
                .thenReturn("Предмет видалено: ID " + id)
                .defaultIfEmpty("Не вдалося видалити предмет.");
    }
}
