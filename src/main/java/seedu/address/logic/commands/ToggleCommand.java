package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.BrowserPanel;

/**
 * Toggles Browser function of the Select Command.
 */
public class ToggleCommand extends Command {

    public static final String COMMAND_WORD = "toggle";

    public static final String TOGGLE_SUCCESS = "Browser Panel Toggled to ";

    @Override
    public CommandResult execute() throws CommandException {
        BrowserPanel.setBrowserMode();
        return new CommandResult(composeCommandResult());
    }

    private String composeCommandResult() {
        if(BrowserPanel.getBrowserMode()) {
            return TOGGLE_SUCCESS + "Display Address";
        }
        else {
            return TOGGLE_SUCCESS + "Display Social Media";
        }
    }
}
