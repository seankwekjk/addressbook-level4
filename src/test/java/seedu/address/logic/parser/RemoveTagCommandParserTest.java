package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.RemoveTagCommand;

//@@author justuswah
/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class RemoveTagCommandParserTest {

    private static final String INVALID_TAG = "!invalidTag!";
    private RemoveTagCommandParser parser = new RemoveTagCommandParser();

    @Test
    public void parse_invalidArgument_throwsParseException() {
        assertParseFailure(parser, INVALID_TAG,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
    }

}
