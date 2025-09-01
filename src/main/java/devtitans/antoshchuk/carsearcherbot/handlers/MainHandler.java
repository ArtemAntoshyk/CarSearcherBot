package devtitans.antoshchuk.carsearcherbot.handlers;

import devtitans.antoshchuk.carsearcherbot.bot.CarSearcherBot;
import devtitans.antoshchuk.carsearcherbot.util.keyboard.KeyboardFactory;
import devtitans.antoshchuk.carsearcherbot.service.message.MessageService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class MainHandler {
    private CarSearcherBot bot;
    private KeyboardFactory keyboardFactory;
    private MessageService messageService;

    public MainHandler(@Lazy CarSearcherBot bot) {
        this.bot = bot;
        keyboardFactory = new KeyboardFactory();
        messageService = new MessageService(bot);
    }
}
