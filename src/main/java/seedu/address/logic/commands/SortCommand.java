package seedu.address.logic.commands;

/**
 * Lists all persons in Contags to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "List has been sorted.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the list by name, phone, email, address or tag.\n"
            + "Parameters: KEYWORD\n"
            + "Example for name sort: " + COMMAND_WORD + " name\n"
            + "Example for email sort: " + COMMAND_WORD + " email";

    public final String toSort;

    public SortCommand(String toSort) {
        this.toSort = toSort;
    }

    @Override
    public CommandResult execute() {
        model.sortList(toSort);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
