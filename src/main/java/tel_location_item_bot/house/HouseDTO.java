package tel_location_item_bot.house;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class HouseDTO {

    private Long id;
    private String name;
    private String address;
}