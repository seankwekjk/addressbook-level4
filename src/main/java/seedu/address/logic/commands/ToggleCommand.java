package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.BrowserPanel;

public class ToggleCommand extends Command{

    public static final String COMMAND_WORD = "toggle";

    public static final String TOGGLE_SUCCESS = "Browser Panel Toggled to ";

    @Override
    public CommandResult execute() throws CommandException {
        BrowserPanel.browserMode=!BrowserPanel.browserMode;
        return new CommandResult(composeCommandResult());
    }

    private String composeCommandResult(){
        return TOGGLE_SUCCESS + BrowserPanel.browserMode.toString();
    }
}
