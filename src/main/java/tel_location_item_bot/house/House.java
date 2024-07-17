package tel_location_item_bot.house;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class House {

    private Long id;
    private String name;
    private String address;

    @Override
    public String toString() {
        return "id  " + id + "  -  " + name  + "  ->  " + address;
    }
}
