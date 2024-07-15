package tel_location_item_bot.room;

import lombok.Data;

import tel_location_item_bot.house.HouseDTO;

@Data
public class RoomDTO {

    private Long id;

    private String name;

    private HouseDTO house;
}