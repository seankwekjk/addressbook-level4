package seedu.address.logic.commands;

import java.util.List;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Sends a SMS message to an existing person in the address book.
 */

public class SmsCommand extends Command {

    public static final String COMMAND_WORD = "sms";
    public static final String ACCOUNT_SID = "ACed7baf2459e41d773a5f9c2232d4d975";
    public static final String AUTH_TOKEN = "6a26cc5c91ff355ebf48fe019700920b";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": sends a phone SMS message to the phone number of the contact identified by the index number\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SMS_PERSON_SUCCESS = "Message Delivered";

    private final Index targetIndex;

    public SmsCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        String receivingNumber = lastShownList.get(targetIndex.getOneBased()).getPhone().toString();

        Message message = Message
                .creator(new PhoneNumber("+65" + receivingNumber), new PhoneNumber("+12082157763"),
                        "Hello!")
                .setMediaUrl("https://climacons.herokuapp.com/clear.png")
                .create();

        return new CommandResult(MESSAGE_SMS_PERSON_SUCCESS);
    }

}
