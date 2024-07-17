package tel_location_item_bot.bot;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public final class BotException extends RuntimeException {

    public BotException(final TelegramApiException message) {
        super(message);
    }
}
