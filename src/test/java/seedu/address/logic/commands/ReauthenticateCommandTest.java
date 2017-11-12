package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_REAUTHENTICATION_FAILURE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ReauthenticateCommand.REAUTHENTICATE_SUCCESS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code ReauthenticateCommand}.
 */
//@@author justuswah
public class ReauthenticateCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_reauthenticateValidParticulars_success() throws Exception {
        String accountId = "ACed7baf2459e41d773a5f9c2232d4d975";
        String authenticationToken = "6a26cc5c91ff355ebf48fe019700920b";
        String sendingNumber = "+12082157763";
        ReauthenticateCommand reauthenticateCommand = prepareCommand(accountId, authenticationToken, sendingNumber);
        String expectedMessage = REAUTHENTICATE_SUCCESS;
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.reauthenticate(accountId, authenticationToken, sendingNumber);
        assertCommandSuccess(reauthenticateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_rejectInvalidParticulars_success() throws Exception {
        String accountId = "invalid";
        String authenticationToken = "invalid";
        String sendingNumber = "invalid";
        ReauthenticateCommand reauthenticateCommand = prepareCommand(accountId, authenticationToken, sendingNumber);
        String expectedMessage = MESSAGE_REAUTHENTICATION_FAILURE;
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.reauthenticate(accountId, authenticationToken, sendingNumber);
        assertCommandSuccess(reauthenticateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        String firstAccountId = "firstInvalidId";
        String firstAuthenticationToken = "firstAuthenticationToken";
        String firstSendingNumber = "firstSendingNumber";
        String secondAccountId = "secondInvalidId";
        String secondAuthenticationToken = "secondAuthenticationToken";
        String secondSendingNumber = "secondSendingNumber";
        ReauthenticateCommand firstCommand =
                new ReauthenticateCommand(firstAccountId, firstAuthenticationToken, firstSendingNumber);
        ReauthenticateCommand secondCommand =
                new ReauthenticateCommand(secondAccountId, secondAuthenticationToken, secondSendingNumber);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different person -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

    /**
     * Returns a {@code ReauthenticateCommand} with the parameter {@code toRemove}.
     */
    private ReauthenticateCommand prepareCommand(String accountId, String authenticationToken, String sendingNumber) {
        ReauthenticateCommand reauthenticateCommand =
                new ReauthenticateCommand(accountId, authenticationToken, sendingNumber);
        reauthenticateCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return reauthenticateCommand;
    }
}
