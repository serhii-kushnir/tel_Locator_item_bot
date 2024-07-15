package tel_location_item_bot.cell;

import lombok.Data;

import tel_location_item_bot.room.Room;

@Data
public class Cell {

    private Long id;

    private String name;

    private Room room;

    @Override
    public String toString() {
        return "id  " + id + "  -  " + name  + "  ->  " + room.getName();
    }
}
