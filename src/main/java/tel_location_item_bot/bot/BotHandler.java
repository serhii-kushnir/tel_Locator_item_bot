package tel_location_item_bot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

import tel_location_item_bot.box.BoxHandler;
import tel_location_item_bot.house.HouseHandler;

import tel_location_item_bot.item.ItemHandler;
import tel_location_item_bot.item.ItemService;

import tel_location_item_bot.room.RoomHandler;

@Service
public class BotHandler {

    private final HouseHandler houseHandler;
    private final RoomHandler roomHandler;
    private final ItemHandler itemHandler;
    private final BoxHandler boxHandler;

    @Autowired
    public BotHandler(final HouseHandler houseHandler,
                      final RoomHandler roomHandler,
                      final ItemHandler itemHandler,
                      final BoxHandler boxHandler) {
        this.houseHandler = houseHandler;
        this.roomHandler = roomHandler;
        this.itemHandler = itemHandler;
        this.boxHandler = boxHandler;
    }

    public Mono<String> processMessage(final String messageText) {
        if (messageText.equals("/start")) {
            return Mono.just("Вітаю! Це ваш Telegram бот для управлінням предметів");
        }

        if (messageText.startsWith("/house")) {
            return houseHandler.handler(messageText);
        }

        if (messageText.startsWith("/room")) {
            return roomHandler.handler(messageText);
        }

        if (messageText.startsWith("/box")) {
            return boxHandler.handler(messageText);
        }

        if (messageText.startsWith("/item")) {
            return itemHandler.handler(messageText);
        }

        return Mono.just("Невідома команда");
    }


}
