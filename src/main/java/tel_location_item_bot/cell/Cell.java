package tel_location_item_bot.cell;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import tel_location_item_bot.room.Room;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cell {

    private Long id;
    private String name;
    private Room room;

    @Override
    public String toString() {
        return "id  " + id + "  -  " + name  + "  ->  " + room.getName();
    }
}
