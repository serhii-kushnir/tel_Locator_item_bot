package tel_location_item_bot.room;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import tel_location_item_bot.house.HouseService;

import java.util.Arrays;
import java.util.List;

@Service
public final class RoomService {

    public static final String PREFIX_ROOM = "/room";
    private final WebClient webClient;
    private final HouseService houseService;

    public RoomService(final WebClient webClient, final HouseService houseService) {
        this.webClient = webClient;
        this.houseService = houseService;
    }

    public Mono<Room> createRoom(final RoomDTO roomDTO) {
        return webClient.post()
                .uri(PREFIX_ROOM + "/create")
                .body(Mono.just(roomDTO), RoomDTO.class)
                .retrieve()
                .bodyToMono(Room.class);
    }

    public Mono<List<Room>> getListRooms() {
        return webClient.get()
                .uri(PREFIX_ROOM + "/list")
                .retrieve()
                .bodyToMono(Room[].class)
                .map(Arrays::asList);
    }

    public Mono<Room> getRoomById(final Long id) {
        return webClient.get()
                .uri(PREFIX_ROOM + "/" + id)
                .retrieve()
                .bodyToMono(Room.class);
    }

    public Mono<Room> editRoomById(final RoomDTO roomDTO, final Long id) {
        return webClient.post()
                .uri(PREFIX_ROOM + "/edit/" + id)
                .body(Mono.just(roomDTO), Room.class)
                .retrieve()
                .bodyToMono(Room.class);
    }

    public Mono<Void> deleteRoomById(final Long id) {
        return webClient.post()
                .uri(PREFIX_ROOM + "/delete/" + id)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public RoomDTO convertRoomToRoomDTO(final Room room) {
        return RoomDTO.builder()
                .id(room.getId())
                .name(room.getName())
                .house(houseService.convertHouseToHouseDTO(room.getHouse()))
                .build();
    }
}
