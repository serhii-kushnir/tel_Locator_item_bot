package tel_location_item_bot.box;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

import static tel_location_item_bot.utility.Constant.PREFIX_BOX;

@Component
public class BoxHandler {

    private final BoxCommand boxCommand;

    @Autowired
    public BoxHandler(final BoxCommand boxCommand) {
        this.boxCommand = boxCommand;
    }

    public Mono<String> handler(final String messageText) {
        String[] parts = messageText.split(" ", 2);
        String command = parts[0];
        String arguments = parts.length > 1 ? parts[1] : "";

        return switch (command) {
            case PREFIX_BOX + "list" -> boxCommand.getList();
            case PREFIX_BOX + "create" -> boxCommand.create("/box/create " + arguments);
            case "/box" -> boxCommand.getById("/box " + arguments);
            case PREFIX_BOX + "edit" -> boxCommand.edit("/box/edit " + arguments);
//            case PREFIX_BOX + "delete" -> boxCommand.delete("/box/delete " + arguments);
            default -> Mono.just("Невідома команда для /box");
        };
    }
}
