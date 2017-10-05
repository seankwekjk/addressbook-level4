package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Remark;

import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";
    public Remark remark;
    public Index index;

    public RemarkCommand(Remark args, Index index){
        remark =args;
        this.index=index;
    }

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a remark to a person in the address book. "
            + "Parameters: "
            + "INDEX "
            + PREFIX_REMARK + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 " + PREFIX_REMARK
            + "Likes to drink coffee.";

    public CommandResult executeUndoableCommand() throws CommandException{
        String remarkText=remark.toString()+" "+index.getOneBased();
        throw new CommandException(remarkText);
    }
}
