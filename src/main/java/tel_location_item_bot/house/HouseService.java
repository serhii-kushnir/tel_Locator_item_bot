package tel_location_item_bot.house;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Service
public class HouseService {

    public static final String PREFIX_HOUSE = "/house";
    private final WebClient webClient;

    public HouseService(final WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<House> createHouse(final House house) {
        return webClient.post()
                .uri(PREFIX_HOUSE + "/create")
                .body(Mono.just(house), House.class)
                .retrieve()
                .bodyToMono(House.class);
    }

    public Mono<List<House>> getListHouses() {
        return webClient.get()
                .uri(PREFIX_HOUSE + "/list")
                .retrieve()
                .bodyToMono(House[].class)
                .map(Arrays::asList);
    }

    public Mono<House> getHouseById(final Long id) {
        return webClient.get()
                .uri(PREFIX_HOUSE + "/" + id)
                .retrieve()
                .bodyToMono(House.class);
    }

    public Mono<House> editHouseById(final House house, final Long id) {
        return webClient.post()
                .uri(PREFIX_HOUSE + "/edit/" + id)
                .body(Mono.just(house), House.class)
                .retrieve()
                .bodyToMono(House.class);
    }

    public Mono<Void> deleteHouseById(final Long id) {
        return webClient.post()
                .uri(PREFIX_HOUSE + "/delete/" + id)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public HouseDTO convertHouseToHouseDTO(House house) {
        HouseDTO houseDTO = new HouseDTO();
        houseDTO.setId(house.getId());
        houseDTO.setName(house.getName());
        return houseDTO;
    }
}