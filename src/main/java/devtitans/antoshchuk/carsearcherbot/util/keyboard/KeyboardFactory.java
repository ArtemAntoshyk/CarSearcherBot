package devtitans.antoshchuk.carsearcherbot.util.keyboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class KeyboardFactory {
    private KeyboardLoader keyboardLoader;

    @Autowired
    public KeyboardFactory(KeyboardLoader keyboardLoader) {
        this.keyboardLoader = keyboardLoader;
    }

    private ReplyKeyboardMarkup getReplyKeyboard(List<List<String>> buttons) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        for (List<String> row : buttons) {
            KeyboardRow keyboardRow = new KeyboardRow();
            for (String button : row) {
                String[] buttonParts = button.split("->");
                String text = buttonParts[0];
                KeyboardButton keyboardButton = new KeyboardButton(text);
                if (buttonParts.length == 2) {
                    if(buttonParts[1].equals("request_phone")) keyboardButton.setRequestContact(true);
                }
                keyboardRow.add(keyboardButton);
            }
            keyboard.add(keyboardRow);
        }

        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        return replyKeyboardMarkup;
    }


    private InlineKeyboardMarkup getInlineKeyboard(List<List<LinkedHashMap<String, String>>> buttons) {
        List<List<InlineKeyboardButton>> inlineRows = new ArrayList<>();
        for(List<LinkedHashMap<String, String>> row : buttons) {
            List<InlineKeyboardButton> rowButtons = new ArrayList<>();
            for(LinkedHashMap<String, String> entry : row) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(entry.get("text"));
                button.setCallbackData(entry.get("callback"));
                rowButtons.add(button);
            }
            inlineRows.add(rowButtons);
        }
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(inlineRows);
        return inlineKeyboardMarkup;
    }


    public ReplyKeyboard getKeyboard(KeyboardLoader.Keyboard keyboard){
        KeyboardConfig keyboardConfig = keyboardLoader.getButtonsByName(keyboard);
        switch (keyboardConfig.getType()){
            case "REPLY"->{
                return getReplyKeyboard(keyboardConfig.getReplyButtons());
            }
            case "INLINE"->{
                return getInlineKeyboard(keyboardConfig.getInlineButtons());
            }
            default ->{
                throw new RuntimeException("Unknown keyboard type");
            }
        }
    }
}
