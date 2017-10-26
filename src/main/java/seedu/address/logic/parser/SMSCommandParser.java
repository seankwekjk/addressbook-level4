package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SMS_Command;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input index and creates a new SMSCommand object
 */
public class SMSCommandParser implements Parser<SMS_Command> {

    /**
     * Parses the given {@code String} of arguments in the context of the SMSCommand
     * and returns an SMSCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SMS_Command parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SMS_Command(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SMS_Command.MESSAGE_USAGE));
        }
    }
}
