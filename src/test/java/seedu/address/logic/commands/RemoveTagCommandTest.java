package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_TAG_NOT_FOUND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code RemoveTagCommand}.
 */
//@@author justuswah
public class RemoveTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_removeFirstTag_success() throws Exception {
        Tag toRemove = model.getAddressBook().getTagList().get(0);
        RemoveTagCommand removeTagCommand = prepareCommand(toRemove);
        String expectedMessage = String.format(Messages.MESSAGE_REMOVE_TAG_SUCCESS, toRemove);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeTag(toRemove);
        assertCommandSuccess(removeTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistentTagName_throwsCommandException() throws Exception {
        Tag invalidTag = new Tag ("nonExistentTag");
        RemoveTagCommand removeTagCommand = prepareCommand(invalidTag);
        assertCommandFailure(removeTagCommand, model, String.format(MESSAGE_TAG_NOT_FOUND));
    }

    @Test
    public void execute_removeLastTag_success() throws Exception {
        int lastTagIndex = model.getAddressBook().getTagList().size() - 1;
        Tag toRemove = model.getAddressBook().getTagList().get(lastTagIndex);
        RemoveTagCommand removeTagCommand = prepareCommand(toRemove);
        String expectedMessage = String.format(Messages.MESSAGE_REMOVE_TAG_SUCCESS, toRemove);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeTag(toRemove);
        assertCommandSuccess(removeTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Tag firstTag = model.getAddressBook().getTagList().get(0);
        Tag secondTag = model.getAddressBook().getTagList().get(1);
        RemoveTagCommand removeFirstCommand = new RemoveTagCommand(firstTag);
        RemoveTagCommand removeSecondCommand = new RemoveTagCommand(secondTag);

        // same object -> returns true
        assertTrue(removeFirstCommand.equals(removeFirstCommand));

        // different types -> returns false
        assertFalse(removeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(removeFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(removeFirstCommand.equals(removeSecondCommand));
    }

    /**
     * Returns a {@code RemoveTagCommand} with the parameter {@code toRemove}.
     */
    private RemoveTagCommand prepareCommand(Tag toRemove) {
        RemoveTagCommand removeTagCommand = new RemoveTagCommand(toRemove);
        removeTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removeTagCommand;
    }
}
