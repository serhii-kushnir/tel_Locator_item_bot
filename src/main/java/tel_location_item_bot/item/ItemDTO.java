package tel_location_item_bot.item;

import lombok.Data;

@Data
public class ItemDTO {

    private Long id;

    private String name;

    private String description;

    private Integer quantity;

    private Long roomId;

    private Long houseId;

    private Long boxId;
}

