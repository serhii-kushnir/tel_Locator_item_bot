package tel_location_item_bot.house;

import lombok.Data;

@Data
public class House {

    private Long id;

    private String name;

    private String address;

    @Override
    public String toString() {
        return "id  " + id + "  -  " + name  + "  ->  " + address;
    }
}
