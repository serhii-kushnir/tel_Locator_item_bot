package tel_location_item_bot.item;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static tel_location_item_bot.utility.Constant.PREFIX_ITEM;

@Service
public class ItemService {

    private final WebClient webClient;

    public ItemService(final WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Item> createItem(final ItemDTO itemDTO) {
        return webClient.post()
                .uri(PREFIX_ITEM + "create")
                .body(Mono.just(itemDTO), ItemDTO.class)
                .retrieve()
                .bodyToMono(Item.class);
    }

    public Mono<List<Item>> getListItems() {
        return webClient.get()
                .uri(PREFIX_ITEM + "list")
                .retrieve()
                .bodyToMono(Item[].class)
                .map(Arrays::asList);
    }

    public Mono<Item> getItemById(final Long id) {
        return webClient.get()
                .uri(PREFIX_ITEM + id)
                .retrieve()
                .bodyToMono(Item.class);
    }

    public Mono<Item> editItemById(final ItemDTO itemDTO, final Long id) {
        return webClient.post()
                .uri(PREFIX_ITEM + "edit/" + id)
                .body(Mono.just(itemDTO), Item.class)
                .retrieve()
                .bodyToMono(Item.class);
    }

    public Mono<Void> deleteItemById(final Long id) {
        return webClient.post()
                .uri(PREFIX_ITEM + "delete/" + id)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
