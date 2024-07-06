package tel_location_item_bot.house;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

import static tel_location_item_bot.utility.Constant.PREFIX_HOUSE;

@Component
public class HouseHandler {

    private final HouseCommand houseCommand;

    @Autowired
    public HouseHandler(final HouseCommand houseCommand) {
        this.houseCommand = houseCommand;
    }

    public Mono<String> handler(final String messageText) {
        String[] parts = messageText.split(" ", 2);
        String command = parts[0];
        String arguments = parts.length > 1 ? parts[1] : "";

        return switch (command) {
            case PREFIX_HOUSE  + "list" -> houseCommand.getListHouse();
            case PREFIX_HOUSE  + "create" -> houseCommand.create("/house/create " + arguments);
            case "/house" -> houseCommand.getById("/house " + arguments);
            case PREFIX_HOUSE  + "edit" -> houseCommand.edit("/house/edit " + arguments);
            case PREFIX_HOUSE  + "delete" -> houseCommand.delete("/house/delete " + arguments);
            default -> Mono.just("Невідома команда для /house");
        };
    }
}
