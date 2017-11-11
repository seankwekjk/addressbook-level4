package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_REMOVE_TAG_SUCCESS;
import static seedu.address.commons.core.Messages.MESSAGE_TAG_NOT_FOUND;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.TagNotFoundException;

public class RemoveTagCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_REMOVE_TAG_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE);

    @Test
    public void removeTag() {

        /* ----------------- Performing removeTag operations with filled list of tags -------------------- */

        /* Case: Deletes the first Tag in the list, with leading and trailing whitespace -> Tag deleted */
        Model expectedModel = getModel();
        Tag toRemove = expectedModel.getAddressBook().getTagList().get(0); //obtaining the first tag, to be removed
        String command = "     " + RemoveTagCommand.COMMAND_WORD + "      "
                + toRemove.getTagName() + "     ";
        Tag deletedTag = removeTag(expectedModel, toRemove);
        String expectedResultMessage = String.format(MESSAGE_REMOVE_TAG_SUCCESS, deletedTag);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: Removes last tag in list -> Last Tag removed */
        Model modelBeforeDeletingLast = getModel();
        int lastIndex = expectedModel.getAddressBook().getTagList().size();
        Tag lastTag = expectedModel.getAddressBook().getTagList().get(lastIndex - 1);
        assertCommandSuccess(lastTag);

        /* Case: undo deleting the last person in the list -> last person restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last person in the list -> last person deleted again */
        command = RedoCommand.COMMAND_WORD;
        removeTag(modelBeforeDeletingLast, lastTag);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* ----------------- Performing invalid removeTag operations -------------------- */

        /* Case: Nonexistent Tag -> rejected */
        command = RemoveTagCommand.COMMAND_WORD + " nonexistentTag";
        assertCommandFailure(command, MESSAGE_TAG_NOT_FOUND);

        /* Case: Recently deleted Tag -> rejected */
        command = RemoveTagCommand.COMMAND_WORD + " " + lastTag.tagName;
        assertCommandFailure(command, MESSAGE_TAG_NOT_FOUND);

        /* Case: Invalid Argument (Non-alphanumeric) -> rejected */
        command = RemoveTagCommand.COMMAND_WORD + " !!" + lastTag.tagName;
        assertCommandFailure(command, MESSAGE_INVALID_REMOVE_TAG_COMMAND_FORMAT);

        /* Case: Invalid Arguments (extra argument) -> rejected */
        command = RemoveTagCommand.COMMAND_WORD + " " + lastTag.tagName + " extraTag";
        assertCommandFailure(command, MESSAGE_INVALID_REMOVE_TAG_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        command = "rEmOvEtAg" + " " + lastTag.tagName;
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);

    }

    /**
     * Removes the specified {@code Tag} in {@code model}'s address book.
     * Returns the deleted Tag
     */
    private Tag removeTag(Model model, Tag toRemove) {
        try {
            model.removeTag(toRemove);
        } catch (TagNotFoundException ive) {
            throw new AssertionError("Tag does not exist.");
        }
        return toRemove;
    }

    /**
     * Deletes the tag at {@code toRemove} by creating a default {@code RemoveTagCommand} using {@code toRemove} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see RemoveTagCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Tag toRemove) {
        Model expectedModel = getModel();
        Tag deletedTag = removeTag(expectedModel, toRemove);
        String expectedResultMessage = String.format(MESSAGE_REMOVE_TAG_SUCCESS, deletedTag);
        assertCommandSuccess(
                RemoveTagCommand.COMMAND_WORD + " " + toRemove.getTagName(),
                expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card remains unchanged even after tag removal.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertStatusBarUnchangedExceptSyncStatus();
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertStatusBarUnchanged();
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
    }
}
