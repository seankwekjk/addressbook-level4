package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_MAIL_FAILURE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@ author hymss
/**
 * Contains integration tests (interaction with the Model) for {@code MailCommand}.
 */

public class MailCommandTest {
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

    /**
     * Executes a {@code MailCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        MailCommand mailCommand = new MailCommand(index);

        try {
            mailCommand.execute();
            fail("The expected CommandException was not thrown.");
            mailCommand.sendMail(index.toString());
        } catch (IOException ioe) {
            assertEquals(expectedMessage, MESSAGE_MAIL_FAILURE);
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        } catch (NullPointerException npe) {
            assertEquals(expectedMessage, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        } catch (URISyntaxException use) {
            assertEquals(expectedMessage, MESSAGE_MAIL_FAILURE);
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        } catch (ParseException e) {
            assertEquals(expectedMessage, MESSAGE_MAIL_FAILURE);
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        } catch (CommandException ce) {
            assertEquals(expectedMessage, MESSAGE_MAIL_FAILURE);
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Parses {@code userInput} into a {@code EmailCommand}.
     */
    private MailCommand prepareCommand(Index index) {
        MailCommand command = new MailCommand(index);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed.
     * Asserts that the command feedback is equal to {@code expectedMessage}.
     * Asserts that the {@code AddressBook} in model remains the same after executing the {@code command}.
     * Asserts that the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}.
     */
    private void assertCommandSuccess(MailCommand command, String expectedMessage, List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        try{
            CommandResult commandResult = command.execute();
            assertEquals(expectedMessage, commandResult.feedbackToUser);
            assertEquals(expectedAddressBook, model.getAddressBook());
            assertEquals(expectedList, model.getFilteredPersonList());
        }
        catch (CommandException ce){
            assertEquals(expectedMessage, MESSAGE_MAIL_FAILURE);
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }

    }
}
