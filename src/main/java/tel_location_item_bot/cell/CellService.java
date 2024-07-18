package tel_location_item_bot.cell;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Service
public class CellService {

    public static final String PREFIX_CELL = "/cell";
    private final WebClient webClient;

    public CellService(final WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Cell> createCell(final CellDTO cellDTO) {
        return webClient.post()
                .uri(PREFIX_CELL + "/create")
                .body(Mono.just(cellDTO), Cell.class)
                .retrieve()
                .bodyToMono(Cell.class);
    }

    public Mono<Cell> getCellById(final Long id) {
        return webClient.get()
                .uri(PREFIX_CELL + "/" +  id)
                .retrieve()
                .bodyToMono(Cell.class);
    }

    public Mono<Cell> editCellById(final CellDTO cellDTO, final Long id) {
        return webClient.post()
                .uri(PREFIX_CELL + "/edit/" + id)
                .body(Mono.just(cellDTO), Cell.class)
                .retrieve()
                .bodyToMono(Cell.class);
    }

    public Mono<Void> deleteCellById(final Long id) {
        return webClient.post()
                .uri(PREFIX_CELL + "/delete/" + id)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<List<Cell>> getListCells() {
        return webClient.get()
                .uri(PREFIX_CELL + "/list")
                .retrieve()
                .bodyToMono(Cell[].class)
                .map(Arrays::asList);
    }
}
