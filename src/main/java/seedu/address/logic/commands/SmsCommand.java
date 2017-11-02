package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_SMS_NUMBER_UNAUTHORIZED;
import static seedu.address.commons.core.Messages.MESSAGE_SMS_PERSON_SUCCESS;

import java.util.List;

import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Sends a SMS message to an existing person in the address book.
 */
//@@author justuswah
public class SmsCommand extends Command {

    public static final String COMMAND_WORD = "sms";
    public static final String ACCOUNT_SID = "ACed7baf2459e41d773a5f9c2232d4d975";
    public static final String AUTH_TOKEN = "6a26cc5c91ff355ebf48fe019700920b";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": sends a phone SMS message to the phone number of the contact identified by the index number\n"
            + "Parameters: INDEX (must be a positive integer), MESSAGE (can be string of any length)\n"
            + "Example: " + COMMAND_WORD + " 1" + " text/hello there";

    private final Index targetIndex;
    private String text = null;

    public SmsCommand(Index targetIndex, String text) {
        this.targetIndex = targetIndex;
        this.text = text;
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            return new CommandResult(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        String receivingNumber = lastShownList.get(targetIndex.getZeroBased()).getPhone().toString();

        try {

            Message message = Message
                    .creator(new PhoneNumber("+65" + receivingNumber), new PhoneNumber("+12082157763"), text)
                    .create();
        } catch (ApiException ae) {
            return new CommandResult(MESSAGE_SMS_NUMBER_UNAUTHORIZED);
        }

        return new CommandResult(MESSAGE_SMS_PERSON_SUCCESS);

    }

}
