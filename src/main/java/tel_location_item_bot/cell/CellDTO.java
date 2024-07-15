package tel_location_item_bot.cell;

import lombok.Data;

import tel_location_item_bot.room.RoomDTO;

@Data
public class CellDTO {

    private Long id;

    private String name;

    private RoomDTO room;

}
