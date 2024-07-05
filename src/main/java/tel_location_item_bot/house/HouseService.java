package tel_location_item_bot.house;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HouseService {

    private final WebClient webClient;

    public HouseService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<List<House>> getAllHouses() {
        return webClient.get()
                .uri("/house/all")
                .retrieve()
                .bodyToMono(House[].class)
                .map(Arrays::asList);
    }

    public String convertHousesToString(List<House> houses) {
        return houses.stream()
                .map(House::toString)
                .collect(Collectors.joining("\n"));
    }
}