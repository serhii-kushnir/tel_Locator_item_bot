package tel_location_item_bot.house;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Service
public class HouseService {

    private final WebClient webClient;

    private static final String PREFIX_HOUSE = "/house/";

    public HouseService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<List<House>> getAllHouses() {
        return webClient.get()
                .uri(PREFIX_HOUSE + "list")
                .retrieve()
                .bodyToMono(House[].class)
                .map(Arrays::asList);
    }

    public Mono<House> getHouseById(Long id) {
        return webClient.get()
                .uri(PREFIX_HOUSE + id)
                .retrieve()
                .bodyToMono(House.class);
    }

    public Mono<House> createHouse(House house) {
        return webClient.post()
                .uri(PREFIX_HOUSE + "create")
                .body(Mono.just(house), House.class)
                .retrieve()
                .bodyToMono(House.class);
    }

}