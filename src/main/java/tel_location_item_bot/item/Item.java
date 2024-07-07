package tel_location_item_bot.item;

import lombok.Data;
import tel_location_item_bot.box.Box;
import tel_location_item_bot.room.Room;

@Data
public class Item {
    private Long id;
    private String name;
    private String description;
    private Integer quantity;
    private Room room;
    private Box box;
    private Long roomId;
    private Long boxId;

    @Override
    public String toString() {
        String roomName = room != null ? room.getName() : "Unknown Room";
        String boxName = box != null ? box.getName() : "Unknown Box";

        return "id  " + id
                + "  -  " + name
                + "  -  " + description
                + "  -  " + quantity
                + "  ->  " + boxName
                + "  ->  " + roomName;
    }

    public String toStringById() {
        String roomName = room != null ? room.getName() : "Unknown Room";
        String boxName = box != null ? box.getName() : "Unknown Box";

        return "id  " + id
                + "  -  " + name
                + "  -  " + description
                + "  -  " + quantity
                + "  ->  " + boxName
                + "  ->  " + roomName;
    }
}
