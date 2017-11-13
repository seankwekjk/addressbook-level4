package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_SMS_NUMBER_UNAUTHORIZED;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SMS_TEXT;

import org.junit.Test;

import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SmsCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;

//@@author justuswah
public class SmsCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void sms() throws Exception {

        /* ----------------- Performing Valid Reauthenticate Operations -------------------- */

        /* Case: Attempts to use SMS command without Index -> Invalid Command Format */
        String message = "hello";
        String command = SmsCommand.COMMAND_WORD + " " + PREFIX_SMS_TEXT + message;
        String expectedResultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SmsCommand.MESSAGE_USAGE);
        assertCommandFailure(command, expectedResultMessage);

        /* Case: Attempts to use SMS command with invalid index -> Returns "invalid index" message*/
        Model expectedModel = getModel();
        command = SmsCommand.COMMAND_WORD + " " + expectedModel.getAddressBook().getPersonList().size() + 1 + " "
                + PREFIX_SMS_TEXT + message;
        expectedResultMessage = MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: Attempts to use SMS command with unauthorized number -> Returns "Number unauthorized" message*/
        expectedModel = getModel();
        command = SmsCommand.COMMAND_WORD + " " + expectedModel.getAddressBook().getPersonList().size() + " "
                + PREFIX_SMS_TEXT + message;
        expectedResultMessage = MESSAGE_SMS_NUMBER_UNAUTHORIZED;
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: Attempts to use SMS command with random whitespace, unauthorized number -> rejected */
        expectedModel = getModel();
        command = SmsCommand.COMMAND_WORD + "         " + expectedModel.getAddressBook().getPersonList().size() + "    "
                + PREFIX_SMS_TEXT + message;
        expectedResultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SmsCommand.MESSAGE_USAGE);
        assertCommandFailure(command, expectedResultMessage);

        /* Case: undo last SMS command -> Undo Fail, SMS Command Not Undoable */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo last SMS command -> redo Fail, SMS Command Not Undoable, so not Redoable */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* ----------------- Performing invalid SMS operations -------------------- */

        /* Case: Nonexistent text message -> rejected */
        command = SmsCommand.COMMAND_WORD + " 1";
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SmsCommand.MESSAGE_USAGE));

        /* Case: text prefix not all small caps -> rejected */
        command = SmsCommand.COMMAND_WORD + " 1 " + "tExt/" + message;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SmsCommand.MESSAGE_USAGE));

        /* Case: Mixed case command word -> Unknown Command */
        command = "sMs" + " 1 " + PREFIX_SMS_TEXT + message;
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);

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
        assertSelectedCardUnchanged();
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
    }
}

