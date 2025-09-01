package devtitans.antoshchuk.carsearcherbot.bot;

import devtitans.antoshchuk.carsearcherbot.handlers.MainHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

//@Slf4j
@Component
public class CarSearcherBot extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    private final MainHandler mainHandler;

    public CarSearcherBot(BotConfig botConfig, MainHandler mainHandler) {
        super(botConfig.getBotToken());
        this.botConfig = botConfig;
        this.mainHandler = mainHandler;
    }

    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotUsername();
    }
}
