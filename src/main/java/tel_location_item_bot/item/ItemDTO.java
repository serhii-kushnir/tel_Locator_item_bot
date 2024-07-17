package tel_location_item_bot.item;

import lombok.Data;

import tel_location_item_bot.cell.CellDTO;
import tel_location_item_bot.room.RoomDTO;

@Data
public class ItemDTO {

    private Long id;

    private String name;

    private String description;

    private Integer quantity;

    private RoomDTO room;

    private CellDTO cell;
}

