package systemtests;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.SortCommand;
import seedu.address.model.Model;

//@@author mzxc152

public class SortCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_SORT_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);
    private static final String NAME_SORT = "name";
    private static final String PHONE_SORT = "phone";
    private static final String EMAIL_SORT = "email";
    private static final String ADDRESS_SORT = "address";
    private static final String TAG_SORT = "tag";

    @Test
    public void sort() {
        Model model = getModel();

        /* Case: sort by name -> list will be sorted alphabetically by name */
        String command = SortCommand.COMMAND_WORD + " " + NAME_SORT;
        String expectedResultMessage = String.format(SortCommand.MESSAGE_SUCCESS, NAME_SORT);
        model.sortList(NAME_SORT);
        assertCommandSuccess(command, expectedResultMessage, model);

        /* Case: sort by phone -> list will be sorted numerically by phone number */
        command = SortCommand.COMMAND_WORD + " " + PHONE_SORT;
        expectedResultMessage = String.format(SortCommand.MESSAGE_SUCCESS, PHONE_SORT);
        model.sortList(PHONE_SORT);
        assertCommandSuccess(command, expectedResultMessage, model);

        /* Case: sort by email -> list will be sorted alphabetically by email */
        command = SortCommand.COMMAND_WORD + " " + EMAIL_SORT;
        expectedResultMessage = String.format(SortCommand.MESSAGE_SUCCESS, EMAIL_SORT);
        model.sortList(EMAIL_SORT);
        assertCommandSuccess(command, expectedResultMessage, model);

        /* Case: sort by address -> list will be sorted alphabetically by address */
        command = SortCommand.COMMAND_WORD + " " + ADDRESS_SORT;
        expectedResultMessage = String.format(SortCommand.MESSAGE_SUCCESS, ADDRESS_SORT);
        model.sortList(ADDRESS_SORT);
        assertCommandSuccess(command, expectedResultMessage, model);

        /* Case: sort by tag -> list will be sorted alphabetically by tag */
        Model modelBeforeSortPhone = getModel();
        command = SortCommand.COMMAND_WORD + " " + ADDRESS_SORT;
        expectedResultMessage = String.format(SortCommand.MESSAGE_SUCCESS, ADDRESS_SORT);
        model.sortList(TAG_SORT);
        assertCommandSuccess(command, expectedResultMessage, model);

        /* Case: keyword is wrong -> rejected */
        assertCommandFailure("sort birthday", MESSAGE_INVALID_SORT_COMMAND_FORMAT);

    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays the success message of executing sort command
     * and the model related components equal to the current model.
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}