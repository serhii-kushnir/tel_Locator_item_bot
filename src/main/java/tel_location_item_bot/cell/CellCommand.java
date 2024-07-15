package tel_location_item_bot.cell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Component
public class CellCommand {

    private static final String CELL_NOT_FOUND = "Не знайдена комірка з ID - ";

    @Autowired
    public CellCommand(CellService cellService) {
        this.cellService = cellService;
    }
    private final CellService cellService;

//    public Mono<String> create(final String message) {
//        String[] parts = message.substring("/cell/create ".length()).split(";");
//        if (parts.length != 2) {
//            return Mono.just("Невірний формат команди. Використовуйте: /cell/create [name];[room id]");
//        }
//
//        long roomId;
//        try {
//            roomId = Long.parseLong(parts[1].trim());
//        } catch (NumberFormatException e) {
//            return Mono.just("Невірний формат ID кімнати.");
//        }
//
//        CellDTO newCellDTO = new CellDTO();
//        newCellDTO.setName(parts[0].trim());
//        newCellDTO.setRoom(room);
//
//        return cellService.createCell(newCellDTO)
//                .map(cell -> "Комірка створена: " + cell.toString())
//                .defaultIfEmpty("Не вдалося створити комірку.");
//    }

    public Mono<String> getList() {
        return cellService.getListCells()
                .map(cells -> cells.stream()
                        .map(Cell::toString)
                        .collect(Collectors.joining("\n")));
    }

    public Mono<String> getById(final String message) {
        String[] parts = message.substring("/cell".length()).split(";");
        if (parts.length != 1) {
            return Mono.just("Невірний формат команди. Використовуйте: /cell [id]");
        }

        try {
            Long id = Long.parseLong(parts[0].trim());

            return cellService.getCellById(id)
                    .map(Cell::toString)
                    .defaultIfEmpty(CELL_NOT_FOUND + id);
        } catch (NumberFormatException e) {
            return Mono.just("Некоректний формат ID.");
        }
    }

//    public Mono<String> edit(final String message) {
//        String[] parts = message.substring("/cell/edit ".length()).split(";");
//        if (parts.length != 3) {
//            return Mono.just("Невірний формат команди. Використовуйте: /cell/edit [id];[name];[room id]");
//        }
//
//        Long cellId;
//        try {
//            cellId = Long.parseLong(parts[0].trim());
//        } catch (NumberFormatException e) {
//            return Mono.just("Невірний формат ID боксу.");
//        }
//
//        long roomId;
//        try {
//            roomId = Long.parseLong(parts[2].trim());
//        } catch (NumberFormatException e) {
//            return Mono.just("Невірний формат ID кімнати.");
//        }
//
//        CellDTO editedCellDTO = new CellDTO();
//        editedCellDTO.setId(cellId);
//        editedCellDTO.setName(parts[1].trim());
//        editedCellDTO.setRoom(roomId);
//
//        return cellService.editCellById(editedCellDTO, cellId)
//                .map(cell -> "Комірка з ID " + cell.getId() + " відредагована: " + cell.toString())
//                .defaultIfEmpty("Не вдалося знайти комірку для редагування.");
//    }

    public Mono<String> delete(final String message) {
        String roomIdStr = message.substring("/cell/delete ".length()).trim();

        long cellId;
        try {
            cellId = Long.parseLong(roomIdStr);
        } catch (NumberFormatException e) {
            return Mono.just("Невірний формат ID комірки для видалення.");
        }

        return cellService.deleteCellById(cellId)
                .then(Mono.just("Комірка з ID " + cellId + " видалена."));
    }
}