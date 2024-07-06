package tel_location_item_bot.room;

import lombok.Data;
import tel_location_item_bot.house.House;

@Data
public class Room {

    private Long id;

    private String name;

    private House house;

    @Override
    public String toString() {
        return "id  " + id + "  -  " + name  + "  ->  " + house.getName();
    }
}
