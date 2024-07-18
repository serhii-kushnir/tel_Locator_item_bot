package tel_location_item_bot.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class ItemHandler {

    public static final String PREFIX_ITEM = "/item";
    private final ItemCommand itemCommand;

    @Autowired
    public ItemHandler(final ItemCommand itemCommand) {
        this.itemCommand = itemCommand;
    }

    public Mono<String> handler(final String messageText) {
        String[] parts = messageText.split(" ", 2);
        String command = parts[0];
        String arguments = parts.length > 1 ? parts[1] : "";

        return getcommandItem(command, arguments);
    }

    private Mono<String> getcommandItem(final String command, final String arguments) {
        return switch (command) {
            case PREFIX_ITEM + "/list" -> itemCommand.getList();
            case PREFIX_ITEM + "/create" -> itemCommand.create(PREFIX_ITEM + "/create " + arguments);
            case PREFIX_ITEM -> itemCommand.getById(PREFIX_ITEM + " " + arguments);
            case PREFIX_ITEM + "/edit" -> itemCommand.edit(PREFIX_ITEM + "/edit " + arguments);
            case PREFIX_ITEM + "/delete" -> itemCommand.delete(PREFIX_ITEM + "/delete " + arguments);
            default -> Mono.just("Невідома команда для " + PREFIX_ITEM);
        };
    }
}
