package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.AnyParticularContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new AnyParticularContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    //@@author justuswah
    @Test
    public void parse_validArgsAddressField_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new AnyParticularContainsKeywordsPredicate(Arrays.asList("clementi", "bedok")));
        assertParseSuccess(parser, "clementi bedok", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n clementi \n \t bedok  \t", expectedFindCommand);
    }

    @Test
    public void parse_validArgsPhoneField_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new AnyParticularContainsKeywordsPredicate(Arrays.asList("91234567", "98888888")));
        assertParseSuccess(parser, "91234567 98888888", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n 91234567 \n \t 98888888  \t", expectedFindCommand);
    }

    @Test
    public void parse_validArgsEmailField_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new AnyParticularContainsKeywordsPredicate(Arrays.asList("alice@example.com",
                        "j@j.com")));
        assertParseSuccess(parser, "alice@example.com j@j.com", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n alice@example.com \n \t j@j.com  \t", expectedFindCommand);
    }

    @Test
    public void parse_validArgsBirthdayField_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new AnyParticularContainsKeywordsPredicate(Arrays.asList("01/01/01", "02/02/02")));
        assertParseSuccess(parser, "01/01/01 02/02/02", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n 01/01/01 \n \t 02/02/02  \t", expectedFindCommand);
    }

}
