package tel_location_item_bot.room;

import lombok.Data;

import tel_location_item_bot.house.HouseDTO;

@Data
public class RoomDTO {

    private Long id;

    private String name;

    private HouseDTO house;

    public static RoomDTO fromEntity(final Room room) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(room.getId());
        roomDTO.setName(room.getName());
        roomDTO.setHouse(HouseDTO.fromEntity(room.getHouse()));

        return roomDTO;
    }
}