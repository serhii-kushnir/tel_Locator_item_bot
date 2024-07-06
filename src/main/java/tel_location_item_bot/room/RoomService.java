package tel_location_item_bot.room;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static tel_location_item_bot.utility.Constant.PREFIX_ROOM;

@Service
public class RoomService {

    private final WebClient webClient;

    public RoomService(final WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Room> createRoom(final Room room) {
        return webClient.post()
                .uri(PREFIX_ROOM + "create")
                .body(Mono.just(room), Room.class)
                .retrieve()
                .bodyToMono(Room.class);
    }

    public Mono<List<Room>> getListRooms() {
        return webClient.get()
                .uri(PREFIX_ROOM + "list")
                .retrieve()
                .bodyToMono(Room[].class)
                .map(Arrays::asList);
    }

    public Mono<Room> getRoomById(final Long id) {
        return webClient.get()
                .uri(PREFIX_ROOM + id)
                .retrieve()
                .bodyToMono(Room.class);
    }

    public Mono<Room> editRoomById(final Room room, final Long id) {
        return webClient.post()
                .uri(PREFIX_ROOM + "edit/" + id)
                .body(Mono.just(room), Room.class)
                .retrieve()
                .bodyToMono(Room.class);
    }

    public Mono<Void> deleteRoomById(final Long id) {
        return webClient.post()
                .uri(PREFIX_ROOM + "delete/" + id)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
