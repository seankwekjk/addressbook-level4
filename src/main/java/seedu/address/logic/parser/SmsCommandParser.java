package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SmsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input index and creates a new SMSCommand object
 */
public class SmsCommandParser implements Parser<SmsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SMSCommand
     * and returns an SMSCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SmsCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SmsCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SmsCommand.MESSAGE_USAGE));
        }
    }
}
