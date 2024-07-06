package tel_location_item_bot.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

import static tel_location_item_bot.utility.Constant.PREFIX_ITEM;

@Component
public class ItemHandler {

    private final ItemCommand itemCommand;

    @Autowired
    public ItemHandler(final ItemCommand itemCommand) {
        this.itemCommand = itemCommand;
    }

    public Mono<String> handler(final String messageText) {
        String[] parts = messageText.split(" ", 2);
        String command = parts[0];
//        String arguments = parts.length > 1 ? parts[1] : "";

        return switch (command) {
            case PREFIX_ITEM + "list" -> itemCommand.getList();
//            case PREFIX_ITEM + "create" -> itemCommand.create("/item/create " + arguments);
//            case "/item" -> itemCommand.getById("/item " + arguments);
//            case PREFIX_ITEM + "edit" -> itemCommand.edit("/item/edit " + arguments);
//            case PREFIX_ITEM + "delete" -> itemCommand.delete("/item/delete " + arguments);
            default -> Mono.just("Невідома команда для /item");
        };
    }
}
