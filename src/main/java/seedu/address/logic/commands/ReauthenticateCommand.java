package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_REAUTHENTICATION_FAILURE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACCOUNT_SID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTH_TOKEN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SENDING_NUMBER;

import java.util.logging.Level;

import com.twilio.Twilio;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Reauthenticates the Twilio Account Details & Sending Phone Number for the User
 * Checks if Account Details are correct
 * Returns @MESSAGE_REAUTHENTICATION_FAILURE if account details are incorrect
 */
//@@author justuswah
public class ReauthenticateCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "reauthenticate";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": reauthenticates the Account SID, Authentication Token, and Sending Number. "
            + "Parameters: "
            + PREFIX_ACCOUNT_SID + "ACCOUNT_SID "
            + PREFIX_AUTH_TOKEN + "AUTHENTICATION_TOKEN "
            + PREFIX_SENDING_NUMBER + "SENDING_NUMBER. "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ACCOUNT_SID + "ACed7baf2459e41d773a5f9c2232d4d975 "
            + PREFIX_AUTH_TOKEN + "6a26cc5c91ff355ebf48fe019700920b "
            + PREFIX_SENDING_NUMBER + "+12082157763";
    public static final String REAUTHENTICATE_SUCCESS = "Details Reauthenticated";

    private String accountSid;
    private String authenticationToken;
    private String sendingNumber;

    public ReauthenticateCommand(String accountSid, String authenticationToken, String sendingNumber) {
        this.accountSid = accountSid;
        this.authenticationToken = authenticationToken;
        this.sendingNumber = sendingNumber;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        try {
            Twilio.init(accountSid, authenticationToken);
            com.twilio.rest.lookups.v1.PhoneNumber number = com.twilio.rest.lookups.v1.PhoneNumber
                    .fetcher(new com.twilio.type.PhoneNumber(sendingNumber))
                    .setType("carrier")
                    .fetch();
        } catch (com.twilio.exception.ApiException e) {
            logger.log(Level.INFO, "Authentication Attempt Failed");
            return new CommandResult(MESSAGE_REAUTHENTICATION_FAILURE);
        }

        model.reauthenticate(accountSid, authenticationToken, sendingNumber);
        logger.log(Level.WARNING, REAUTHENTICATE_SUCCESS);
        return new CommandResult(REAUTHENTICATE_SUCCESS);
    }

}
