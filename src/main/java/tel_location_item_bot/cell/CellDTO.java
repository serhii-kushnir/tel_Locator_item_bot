package tel_location_item_bot.cell;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import tel_location_item_bot.room.RoomDTO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CellDTO {

    private Long id;
    private String name;
    private RoomDTO room;
}
