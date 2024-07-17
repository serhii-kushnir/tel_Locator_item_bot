package tel_location_item_bot.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotConfig {

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Value("${telegram.bot.token}")
    private String botToken;

    public String getBotUsername() {
        return botUsername;
    }

    public String getBotToken() {
        return botToken;
    }

    @Bean
    public TelegramLongPollingBot telegramBot() {
        return new Bot();
    }

    @Bean
    public TelegramBotsApi telegramBotsApi() {
        TelegramBotsApi botsApi = null;

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