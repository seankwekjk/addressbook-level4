package seedu.address.logic.commands;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_SMS_NUMBER_UNAUTHORIZED;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.twilio.exception.ApiException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code SmsCommand}.
 */
//@@author justuswah
public class SmsCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_unauthorizedIndex_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        assertExecutionFailure(outOfBoundsIndex, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_zeroIndex_failure() {
        Index outOfBoundsIndex = Index.fromZeroBased(0);
        assertExecutionFailure(outOfBoundsIndex, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_numberNotAuthorized_success() throws Exception {
        Index index = Index.fromZeroBased(0);
        String text = "hello";
        SmsCommand smsCommand = prepareCommand(index, text);
        String expectedMessage = MESSAGE_SMS_NUMBER_UNAUTHORIZED;
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.smsContact(index, text);
        assertCommandSuccess(smsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Index firstIndex = Index.fromZeroBased(0);
        Index secondIndex = Index.fromZeroBased(1);
        String firstText = "firstMessage";
        String secondText = "secondMessage";

        SmsCommand firstCommand =
                new SmsCommand(firstIndex, firstText);
        SmsCommand secondCommand =
                new SmsCommand(secondIndex, secondText);

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
     * Executes a {@code SmsCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SmsCommand smsCommand = new SmsCommand(index, "hi");

        try {
            smsCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (ApiException ae) {
            assertEquals(expectedMessage, MESSAGE_SMS_NUMBER_UNAUTHORIZED);
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        } catch (CommandException ce) {
            assertEquals(expectedMessage, MESSAGE_SMS_NUMBER_UNAUTHORIZED);
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        } catch (NullPointerException npe) {
            assertEquals(expectedMessage, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code ReauthenticateCommand} with the parameter {@code toRemove}.
     */
    private SmsCommand prepareCommand(Index index, String text) {
        SmsCommand smsCommand =
                new SmsCommand(index, text);
        smsCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return smsCommand;
    }
}
