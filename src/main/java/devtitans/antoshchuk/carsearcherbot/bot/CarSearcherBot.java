package devtitans.antoshchuk.carsearcherbot.bot;

import devtitans.antoshchuk.carsearcherbot.handlers.MainHandler;
import devtitans.antoshchuk.carsearcherbot.handlers.UserHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

//@Slf4j
@Component
public class CarSearcherBot extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    private final MainHandler mainHandler;
    private final UserHandler userHandler;

    public CarSearcherBot(BotConfig botConfig, MainHandler mainHandler, UserHandler userHandler) {
        super(botConfig.getBotToken());
        this.botConfig = botConfig;
        this.mainHandler = mainHandler;
        this.userHandler = userHandler;
    }

    @Override
    public void onUpdateReceived(Update update) {
        userHandler.userHandler(update.getMessage());
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotUsername();
    }
}
