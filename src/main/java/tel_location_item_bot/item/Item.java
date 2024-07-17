package tel_location_item_bot.item;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import tel_location_item_bot.cell.Cell;
import tel_location_item_bot.room.Room;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private Long id;
    private String name;
    private String description;
    private Integer quantity;
    private Room room;
    private Cell cell;

    @Override
    public String toString() {
        String cellName = cell != null ? cell.getName() : "null";

        return "id  " + id
                + "  -  " + name
                + "  -  " + description
                + "  -  " + quantity
                + "  ->  " + cellName
                + "  ->  " + room.getName();
    }
}
