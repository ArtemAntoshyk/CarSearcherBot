package devtitans.antoshchuk.carsearcherbot.util.message;

import devtitans.antoshchuk.carsearcherbot.bot.CarSearcherBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Service
public class MessageService {
    private CarSearcherBot bot;

    public MessageService(CarSearcherBot bot) {
        this.bot = bot;
    }

    public void updateInlineKeyboard(long chatId, int messageId, InlineKeyboardMarkup newKeyboard) {
        EditMessageReplyMarkup editMarkup = new EditMessageReplyMarkup();
        editMarkup.setChatId(chatId);
        editMarkup.setMessageId(messageId);
        editMarkup.setReplyMarkup(newKeyboard);

        try {
            bot.execute(editMarkup);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteMessage(Long chatId, int messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(messageId);
        try {
            bot.execute(deleteMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(long chatId, String text, Object replyMarkup) {
        SendMessage message = new SendMessage();
        System.out.println(chatId);
        message.setChatId(chatId);
        message.setText(text);
        message.setParseMode("HTML");
        if (replyMarkup != null) message.setReplyMarkup((ReplyKeyboard) replyMarkup);
        try {
            bot.execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(long chatId, String text) {
        sendMessage(chatId, text, null);
    }
}
