package tel_location_item_bot.bot;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Getter
@Configuration
public class BotConfig {

    private final String botUsername;
    private final String botToken;

    public BotConfig(@Value("${telegram.bot.username}") final String botUsername,
                     @Value("${telegram.bot.token}") final String botToken) {
        this.botUsername = botUsername;
        this.botToken = botToken;
    }

    @Bean
    public TelegramLongPollingBot telegramBot() {
        return new Bot();
    }

    @Bean
    public TelegramBotsApi telegramBotsApi() {
        TelegramBotsApi botsApi;

        try {
            botsApi = new TelegramBotsApi(DefaultBotSession.class);
        } catch (TelegramApiException e) {
            throw new BotException(e);
        }

        try {
            botsApi.registerBot(telegramBot());
        } catch (TelegramApiException e) {
            throw new BotException(e);
        }
        return botsApi;
    }
}