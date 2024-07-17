package tel_location_item_bot.house;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public final class HouseHandler {

    public static final String PREFIX_HOUSE = "/house";
    private final HouseCommand houseCommand;

    @Autowired
    public HouseHandler(final HouseCommand houseCommand) {
        this.houseCommand = houseCommand;
    }

    public Mono<String> handler(final String messageText) {
        String[] parts = messageText.split(" ", 2);
        String command = parts[0];
        String arguments = parts.length > 1 ? parts[1] : "";

        return getCommandHouse(command, arguments);
    }

    private Mono<String> getCommandHouse(String command, String arguments) {
        return switch (command) {
            case PREFIX_HOUSE + "/list" -> houseCommand.getListHouse();
            case PREFIX_HOUSE + "/create" -> houseCommand.create(PREFIX_HOUSE + "/create " + arguments);
            case PREFIX_HOUSE -> houseCommand.getById(PREFIX_HOUSE + " " + arguments);
            case PREFIX_HOUSE + "/edit" -> houseCommand.edit(PREFIX_HOUSE + "/edit " + arguments);
            case PREFIX_HOUSE + "/delete" -> houseCommand.delete(PREFIX_HOUSE + "/delete " + arguments);
            default -> Mono.just("Невідома команда для " + PREFIX_HOUSE);
        };
    }
}
