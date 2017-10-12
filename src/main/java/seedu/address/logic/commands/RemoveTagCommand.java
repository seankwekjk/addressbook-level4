package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;


import java.util.List;

/**
 * Deletes a tag from the entire AddressBook
 */

public class RemoveTagCommand extends UndoableCommand{

    public static final String COMMAND_WORD = "removeTag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the specified tag from all contacts in the AddressBook\n"
            + "Parameters: (TAG_NAME) \n"
            + "Example: " + COMMAND_WORD + " friends";

    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Tag Removed";

    private final Tag toRemove;

    public RemoveTagCommand(Tag toRemove) {
        this.toRemove = toRemove;
    }

    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        if (!model.getAddressBook().getTagList().contains(toRemove)) {
            throw new CommandException(Messages.MESSAGE_INVALID_TAG);
        }
        else{
            model.removeTag(toRemove);;
            return new CommandResult(String.format(MESSAGE_REMOVE_TAG_SUCCESS));
        }
    }


}
