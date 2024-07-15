package tel_location_item_bot.cell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

import static tel_location_item_bot.utility.Constant.PREFIX_CELL;

@Component
public class CellHandler {

    private final CellCommand cellCommand;

    @Autowired
    public CellHandler(final CellCommand cellCommand) {
        this.cellCommand = cellCommand;
    }

    public Mono<String> handler(final String messageText) {
        String[] parts = messageText.split(" ", 2);
        String command = parts[0];
        String arguments = parts.length > 1 ? parts[1] : "";

        return switch (command) {
            case PREFIX_CELL + "list" -> cellCommand.getList();
//            case PREFIX_CELL + "create" -> cellCommand.create("/cell/create " + arguments);
//            case "/cell" -> cellCommand.getById("/cell " + arguments);
//            case PREFIX_CELL + "edit" -> cellCommand.edit("/cell/edit " + arguments);
            case PREFIX_CELL + "delete" -> cellCommand.delete("/cell/delete " + arguments);
            default -> Mono.just("Невідома команда для /cell");
        };
    }
}
