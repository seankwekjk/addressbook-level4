package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACCOUNT_SID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTH_TOKEN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SENDING_NUMBER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.ReauthenticateCommand;

//@@author justuswah
/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class ReauthenticateCommandParserTest {

    private ReauthenticateCommandParser parser = new ReauthenticateCommandParser();

    @Test
    public void parse_missingNumberArgument_throwsParseException() {
        String missingField = PREFIX_ACCOUNT_SID + " " + PREFIX_AUTH_TOKEN + " ";
        assertParseFailure(parser, missingField,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReauthenticateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingAuthTokenArgument_throwsParseException() {
        String missingField = PREFIX_ACCOUNT_SID + " " + PREFIX_SENDING_NUMBER + " ";
        assertParseFailure(parser, missingField,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReauthenticateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingAccountSid_throwsParseException() {
        String missingField = PREFIX_SENDING_NUMBER + " " + PREFIX_AUTH_TOKEN + " ";
        assertParseFailure(parser, missingField,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReauthenticateCommand.MESSAGE_USAGE));
    }
}
