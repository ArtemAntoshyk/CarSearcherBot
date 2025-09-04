package devtitans.antoshchuk.carsearcherbot.handlers;

import devtitans.antoshchuk.carsearcherbot.service.UserService;
import devtitans.antoshchuk.carsearcherbot.service.message.MessageService;
import devtitans.antoshchuk.carsearcherbot.util.fsm.context.RegistrationContext;
import devtitans.antoshchuk.carsearcherbot.util.fsm.service.RegistrationService;
import devtitans.antoshchuk.carsearcherbot.util.fsm.state.RegistrationEvents;
import devtitans.antoshchuk.carsearcherbot.util.fsm.state.RegistrationStates;
import devtitans.antoshchuk.carsearcherbot.util.keyboard.KeyboardFactory;
import devtitans.antoshchuk.carsearcherbot.util.keyboard.KeyboardLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class UserHandler {
    private final RegistrationService registrationService;
    private final UserService userService;
    private final MessageService messageService;
    private final KeyboardFactory keyboardFactory;

    @Autowired
    public UserHandler(RegistrationService registrationService,
                       UserService userService,
                       MessageService messageService,
                       KeyboardFactory keyboardFactory) {
        this.registrationService = registrationService;
        this.userService = userService;
        this.messageService = messageService;
        this.keyboardFactory = keyboardFactory;
    }

    public void userHandler(Message message) {
        if (message.hasContact()) {
            registrationProcess(message.getChatId(), message.getContact().getPhoneNumber(), message);
        } else {
            registrationProcess(message.getChatId(), message.getText(), message);
        }
    }

    private void registrationProcess(Long chatId, String input, Message message) {
        StateMachine<RegistrationStates, RegistrationEvents> machine = registrationService.getUserMachine(chatId);
        RegistrationStates state = machine.getState().getId();

        RegistrationContext context = (RegistrationContext) machine.getExtendedState()
                .getVariables()
                .computeIfAbsent("context", k -> new RegistrationContext());

        switch (state) {
            case START -> {
                registrationService.sendEvent(chatId, RegistrationEvents.START_REGISTRATION);
                messageService.sendMessage(chatId, "Введіть ваше Прізвище та Ім'я✍️");
            }
            case ENTER_NAME -> {
                String[] fullName = input.trim().split("\\s+");
                if (fullName.length < 2) {
                    messageService.sendMessage(chatId, "Будь ласка, введіть і Прізвище, і Ім’я (наприклад: Іваненко Петро)");
                    return;
                }
                context.setLastName(fullName[0]);
                context.setFirstName(fullName[1]);
                registrationService.sendEvent(chatId, RegistrationEvents.NAME_ENTERED);
                messageService.sendMessage(chatId, "Введіть вашу електронну пошту📧");
            }
            case ENTER_EMAIL -> {
                if (!input.contains("@")) {
                    messageService.sendMessage(chatId, "Некоректний формат пошти. Спробуйте ще раз 📧");
                    return;
                }
                context.setEmail(input);
                registrationService.sendEvent(chatId, RegistrationEvents.EMAIL_ENTERED);
                messageService.sendMessage(chatId, "Введіть свій номер телефону☎️",
                        keyboardFactory.getKeyboard(KeyboardLoader.Keyboard.SHARE_CONTACT));
            }
            case ENTER_PHONE_NUMBER -> {
                if (!input.matches("\\+?\\d{10,15}")) {
                    messageService.sendMessage(chatId, "Некоректний номер телефону. Спробуйте ще раз ☎️");
                    return;
                }
                context.setPhone(input);
                registrationService.sendEvent(chatId, RegistrationEvents.PHONE_NUMBER_ENTERED);
                messageService.sendMessage(chatId,
                        "Перевірте введені дані:\n" +
                                "Прізвище та Ім'я: " + context.getLastName() + " " + context.getFirstName() + "\n" +
                                "Електронна пошта: " + context.getEmail() + "\n" +
                                "Номер телефону: " + context.getPhone() + "\n" +
                                "Все вірно?⬆️",
                        keyboardFactory.getKeyboard(KeyboardLoader.Keyboard.SUBMIT_MENU));
            }
            case CONFIRM -> {
                String answer = input;
                if (answer.equals("Так✅")) {
                    String username = message.getFrom().getUserName();
                    long id = message.getFrom().getId();
                    userService.registerUser(userService.createUser(context, username, id));
                    messageService.sendMessage(chatId, "✅ Реєстрація успішна!", KeyboardLoader.Keyboard.MAIN_MENU);
                } else {
                    messageService.sendMessage(chatId, "❌ Дані відхилені. Почнімо спочатку.");
//                    registrationService.sendEvent(chatId, RegistrationEvents.RESTART);
                }
            }
            default -> messageService.sendMessage(chatId, "⚠️ Невідомий стан. Спробуйте ще раз.");
        }
    }
}
