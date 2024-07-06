package tel_location_item_bot.box;

import lombok.Data;
import tel_location_item_bot.room.Room;

@Data
public class Box {

    private Long id;
    private String name;
    private Room room;

    @Override
    public String toString() {
        return "id  " + id + "  -  " + name  + "  ->  " + room.getName();

    }
}
