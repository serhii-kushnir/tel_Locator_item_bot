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
        return "Room{" +
                "id=" + id +
                ", name='" + (name != null ? name : "Unknown") + '\'' +
                ", house=" + (house != null ? house.getName() : "None") +
                '}';
    }
}
