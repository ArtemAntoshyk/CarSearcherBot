package devtitans.antoshchuk.carsearcherbot.util.keyboard;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class KeyboardLoader {
    @Getter
    public enum Keyboard {

        START_MENU("start_menu"),
        MAIN_MENU("main_menu"),
        SETTINGS_MENU("settings_menu"),
        SUBMIT_MENU("submit_menu"),
        SHARE_CONTACT("share_contact");

        private final String type;

        Keyboard(String type) {
            this.type = type;
        }

        public static Keyboard fromType(String type) {
            for (Keyboard k : values()) {
                if (k.type.equalsIgnoreCase(type)) {
                    return k;
                }
            }
            throw new IllegalArgumentException("No enum constant with type " + type);
        }
    }

    public enum ReplyType{
        REPLY,
        INLINE
    }

    private final Map<Keyboard, KeyboardConfig> keyboardCache;

    public KeyboardLoader() {
        this.keyboardCache = loadKeyboard();
    }

    private Map<Keyboard, KeyboardConfig> loadKeyboard() {
        Map<String, Object> data = loadYaml("/static/keyboards.yml");
        return data.entrySet().stream().collect(Collectors.toMap(
                entry -> Keyboard.fromType(entry.getKey()),
                entry -> buildKeyboardConfig((Map<String, Object>) entry.getValue())
        ));
    }

    private KeyboardConfig buildKeyboardConfig(Map<String, Object> raw) {
        KeyboardConfig config = new KeyboardConfig();
        String type = (String) raw.get("type");
        config.setType(type);
        Object buttons = raw.get("buttons");

        ReplyType replyType = ReplyType.valueOf(type.toUpperCase());
        switch (replyType) {
            case REPLY -> config.setReplyButtons((List<List<String>>) buttons);
            case INLINE -> config.setInlineButtons((List<List<LinkedHashMap<String, String>>>) buttons);
        }
        return config;
    }

    private Map<String, Object> loadYaml(String path) {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = KeyboardLoader.class.getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new FileNotFoundException(path + " not found");
            }
            return yaml.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load YAML from " + path, e);
        }
    }

    public KeyboardConfig getButtonsByName(Keyboard key) {
        return keyboardCache.get(key);
    }
}
