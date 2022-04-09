package com.stakhiyevich.openadboard.controller.command;

import com.stakhiyevich.openadboard.controller.command.impl.DefaultCommand;
import com.stakhiyevich.openadboard.controller.command.impl.get.HomePageCommand;
import com.stakhiyevich.openadboard.controller.command.impl.get.SignUpPageCommand;
import com.stakhiyevich.openadboard.controller.command.impl.post.ChangeLanguageCommand;
import com.stakhiyevich.openadboard.controller.command.impl.post.LogOutCommand;
import com.stakhiyevich.openadboard.controller.command.impl.post.SignInCommand;

import java.util.EnumMap;

public final class CommandFactory {

    private static final CommandFactory instance = new CommandFactory();
    private final EnumMap<CommandTypeHolder, Command> commands;

    private CommandFactory() {
        commands = new EnumMap<>(CommandTypeHolder.class);
        commands.put(CommandTypeHolder.DEFAULT_COMMAND, new DefaultCommand());
        commands.put(CommandTypeHolder.HOME_PAGE, new HomePageCommand());
        commands.put(CommandTypeHolder.CHANGE_LANGUAGE, new ChangeLanguageCommand());
        commands.put(CommandTypeHolder.SIGN_IN, new SignInCommand());
        commands.put(CommandTypeHolder.SIGN_UP_PAGE, new SignUpPageCommand());
        commands.put(CommandTypeHolder.LOG_OUT, new LogOutCommand());

    }

    public static CommandFactory getInstance() {
        return instance;
    }

    public Command getCommand(String command) {
        CommandTypeHolder commandTypeHolder = CommandTypeHolder.getCommandType(command);
        return commands.get(commandTypeHolder);
    }
}
