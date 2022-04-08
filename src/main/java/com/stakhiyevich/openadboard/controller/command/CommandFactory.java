package com.stakhiyevich.openadboard.controller.command;

import com.stakhiyevich.openadboard.controller.command.impl.get.HomePageCommand;
import com.stakhiyevich.openadboard.controller.command.impl.post.ChangeLanguageCommand;

import java.util.EnumMap;

public final class CommandFactory {

    private static final CommandFactory instance = new CommandFactory();
    private final EnumMap<CommandTypeHolder, Command> commands;

    private CommandFactory() {
        commands = new EnumMap<>(CommandTypeHolder.class);
        commands.put(CommandTypeHolder.HOME_PAGE, new HomePageCommand());
        commands.put(CommandTypeHolder.CHANGE_LANGUAGE, new ChangeLanguageCommand());
    }

    public static CommandFactory getInstance() {
        return instance;
    }

    public Command getCommand(String command) {
        CommandTypeHolder commandTypeHolder = CommandTypeHolder.getCommandType(command);
        return commands.get(commandTypeHolder);
    }
}
