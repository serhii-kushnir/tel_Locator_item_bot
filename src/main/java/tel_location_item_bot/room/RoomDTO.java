package tel_location_item_bot.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import tel_location_item_bot.house.HouseDTO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class RoomDTO {

    private Long id;
    private String name;
    private HouseDTO house;
}