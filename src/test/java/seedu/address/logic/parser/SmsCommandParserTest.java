package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.SmsCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class SmsCommandParserTest {

    private SmsCommandParser parser = new SmsCommandParser();

    @Test
    public void parse_invalidIndex_returnsSmsCommand() {
        assertParseFailure(parser, " noIndex text/hi",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SmsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noPrefixText_returnsSmsCommand() {
        assertParseFailure(parser, " noIndex messages",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SmsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noPrefixIndex_returnsSmsCommand() {
        assertParseFailure(parser, " text/hi",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SmsCommand.MESSAGE_USAGE));
    }

}
