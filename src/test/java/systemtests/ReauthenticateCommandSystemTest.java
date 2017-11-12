package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_REAUTHENTICATION_FAILURE;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.ReauthenticateCommand.REAUTHENTICATE_SUCCESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACCOUNT_SID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTH_TOKEN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SENDING_NUMBER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ReauthenticateCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;

//@@author justuswah
public class ReauthenticateCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_REAUTHENTICATE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ReauthenticateCommand.MESSAGE_USAGE);

    @Test
    public void reauthenticate() {

        /* ----------------- Performing Valid Reauthenticate Operations -------------------- */

        /* Case: Reauthenticates current particulars to dummy values with random whitespace -> Reauthenticate Failure */
        Model expectedModel = getModel();
        String accountSid = "randomSid";
        String authenticationToken = "randomToken";
        String sendingNumber = "+12345678";
        String command = "     " + ReauthenticateCommand.COMMAND_WORD + "     " + PREFIX_ACCOUNT_SID + accountSid + " "
                + PREFIX_AUTH_TOKEN + authenticationToken + " " + PREFIX_SENDING_NUMBER + sendingNumber;
        String expectedResultMessage = MESSAGE_REAUTHENTICATION_FAILURE;
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: Reauthenticates current particulars to whitespace -> Reauthenticate Failure */
        expectedModel = getModel();
        accountSid = " ";
        authenticationToken = " ";
        sendingNumber = " ";
        command = ReauthenticateCommand.COMMAND_WORD + " " + PREFIX_ACCOUNT_SID + accountSid
                + PREFIX_AUTH_TOKEN + authenticationToken + PREFIX_SENDING_NUMBER + sendingNumber;
        expectedResultMessage = MESSAGE_REAUTHENTICATION_FAILURE;
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: Undoes the previous reauthentication attempt -> Undo Success */
        expectedModel = getModel();
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: redo authentication attempt -> redo success, authentication fails */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: Reauthenticates current particulars to whitespace -> Reauthenticate Failure */
        expectedModel = getModel();
        accountSid = " ";
        authenticationToken = " ";
        sendingNumber = " ";
        command = ReauthenticateCommand.COMMAND_WORD + " " + PREFIX_ACCOUNT_SID + accountSid
                + PREFIX_AUTH_TOKEN + authenticationToken + PREFIX_SENDING_NUMBER + sendingNumber;
        expectedResultMessage = MESSAGE_REAUTHENTICATION_FAILURE;
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: Reauthenticates current particulars to actual account -> Reauthenticate Success */
        expectedModel = getModel();
        accountSid = "ACed7baf2459e41d773a5f9c2232d4d975";
        authenticationToken = "6a26cc5c91ff355ebf48fe019700920b";
        sendingNumber = "+12082157763";
        command = ReauthenticateCommand.COMMAND_WORD + " " + PREFIX_ACCOUNT_SID + accountSid + " "
                + PREFIX_AUTH_TOKEN + authenticationToken + " " + PREFIX_SENDING_NUMBER + sendingNumber;
        expectedResultMessage = REAUTHENTICATE_SUCCESS;
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: Swaps Order of Fields Presented -> Reauthenticate Success */
        expectedModel = getModel();
        accountSid = "ACed7baf2459e41d773a5f9c2232d4d975";
        authenticationToken = "6a26cc5c91ff355ebf48fe019700920b";
        sendingNumber = "+12082157763";
        command = ReauthenticateCommand.COMMAND_WORD + " " + PREFIX_SENDING_NUMBER + sendingNumber + " "
                + PREFIX_AUTH_TOKEN + authenticationToken + " " + PREFIX_ACCOUNT_SID + accountSid;
        expectedResultMessage = REAUTHENTICATE_SUCCESS;
        assertCommandSuccess(command, expectedModel, expectedResultMessage);


        /* ----------------- Performing invalid reauthentication operations -------------------- */

        /* Case: Missing Number Field -> rejected */
        command = ReauthenticateCommand.COMMAND_WORD + " " + PREFIX_ACCOUNT_SID + accountSid + " "
                + PREFIX_AUTH_TOKEN + authenticationToken + " ";
        assertCommandFailure(command, MESSAGE_INVALID_REAUTHENTICATE_COMMAND_FORMAT);

        /* Case: Missing Authentication Token Field -> rejected */
        command = ReauthenticateCommand.COMMAND_WORD + " " + PREFIX_ACCOUNT_SID + accountSid + " "
                + PREFIX_SENDING_NUMBER + sendingNumber + " ";
        assertCommandFailure(command, MESSAGE_INVALID_REAUTHENTICATE_COMMAND_FORMAT);

        /* Case: Missing Account SID Field -> rejected */
        command = ReauthenticateCommand.COMMAND_WORD + " " + PREFIX_AUTH_TOKEN + authenticationToken + " "
                + PREFIX_SENDING_NUMBER + sendingNumber + " ";
        assertCommandFailure(command, MESSAGE_INVALID_REAUTHENTICATE_COMMAND_FORMAT);

        /* Case: Mixed Case Command Word -> rejected */
        command = "rEaUtHeNtIcATe" + " " + PREFIX_SENDING_NUMBER + sendingNumber + " "
                + PREFIX_AUTH_TOKEN + authenticationToken + " " + PREFIX_ACCOUNT_SID + accountSid;
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
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
    }

}
