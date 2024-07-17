package tel_location_item_bot.cell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class CellHandler {

    public static final String PREFIX_CELL = "/cell";
    private final CellCommand cellCommand;

    @Autowired
    public CellHandler(final CellCommand cellCommand) {
        this.cellCommand = cellCommand;
    }

    public Mono<String> handler(final String messageText) {
        String[] parts = messageText.split(" ", 2);
        String command = parts[0];
        String arguments = parts.length > 1 ? parts[1] : "";

        return getCommandCell(command, arguments);
    }

    private Mono<String> getCommandCell(final String command, final String arguments) {
        return switch (command) {
            case PREFIX_CELL + "/list" -> cellCommand.getList();
            case PREFIX_CELL + "/create" -> cellCommand.create(PREFIX_CELL + "/create " + arguments);
            case PREFIX_CELL -> cellCommand.getById(PREFIX_CELL + " " + arguments);
            case PREFIX_CELL + "/edit" -> cellCommand.edit(PREFIX_CELL + "/edit " + arguments);
            case PREFIX_CELL + "/delete" -> cellCommand.delete(PREFIX_CELL + "/delete " + arguments);
            default -> Mono.just("Невідома команда для " + PREFIX_CELL);
        };
    }
}
