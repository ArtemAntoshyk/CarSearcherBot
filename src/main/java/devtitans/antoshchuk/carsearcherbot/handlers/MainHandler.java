package devtitans.antoshchuk.carsearcherbot.handlers;

import devtitans.antoshchuk.carsearcherbot.bot.CarSearcherBot;
import devtitans.antoshchuk.carsearcherbot.util.keyboard.KeyboardFactory;
import devtitans.antoshchuk.carsearcherbot.util.keyboard.KeyboardLoader;
import devtitans.antoshchuk.carsearcherbot.util.message.MessageService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

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

    public void testHandler(Message message) {
        String text = message.getText();
        switch (text){
            case "/test"->{
                System.out.println("Test");
                KeyboardFactory keyboardFactory = new KeyboardFactory();

                messageService.sendMessage(message.getChatId(), message.getText(), keyboardFactory.getKeyboard(KeyboardLoader.Keyboard.START_MENU));
            }
            case "/test2"->{
                messageService.sendMessage(message.getChatId(), message.getText(), keyboardFactory.getKeyboard(KeyboardLoader.Keyboard.SETTINGS_MENU));
            }
        }
    }
}
