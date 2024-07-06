package tel_location_item_bot.box;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static tel_location_item_bot.utility.Constant.PREFIX_BOX;

@Service
public class BoxService {

    private final WebClient webClient;
    public BoxService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Box> createBox(final BoxDTO boxDTO) {
        return webClient.post()
                .uri(PREFIX_BOX + "create")
                .body(Mono.just(boxDTO), Box.class)
                .retrieve()
                .bodyToMono(Box.class);
    }

    public Mono<Box> getBoxById(final Long id) {
        return webClient.get()
                .uri(PREFIX_BOX + id)
                .retrieve()
                .bodyToMono(Box.class);
    }

    public Mono<Box> editBoxById(final BoxDTO boxDTO, final Long id) {
        return webClient.post()
                .uri(PREFIX_BOX + "edit/" + id)
                .body(Mono.just(boxDTO), Box.class)
                .retrieve()
                .bodyToMono(Box.class);
    }

    public Mono<Void> deleteBoxById(final Long id) {
        return webClient.delete()
                .uri(PREFIX_BOX + "delete/" + id)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
