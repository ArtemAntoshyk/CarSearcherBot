package devtitans.antoshchuk.carsearcherbot.util.keyboard;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.List;

@Setter
@Getter
public class KeyboardConfig {
    private String type;
    private List<List<String>> replyButtons;
    private List<List<LinkedHashMap<String, String>>> inlineButtons;
}
