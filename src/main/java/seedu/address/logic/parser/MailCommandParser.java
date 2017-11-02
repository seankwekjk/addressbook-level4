package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAIL_MESSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAIL_RECEPIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAIL_TITLE;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.MailCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MailCommand object
 */
public class MailCommandParser implements Parser<MailCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the MailCommand
     * and returns an MailCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public MailCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MAIL_RECEPIENT, PREFIX_MAIL_TITLE, PREFIX_MAIL_MESSAGE);
        try {
            Index index = ParserUtil.parseIndex(args);
            String title = String.join("", argMultimap.getAllValues(PREFIX_MAIL_TITLE)).replace(" ", "%20");
            String message = String.join("", argMultimap.getAllValues(PREFIX_MAIL_MESSAGE)).replace(" ", "%20");

            return new MailCommand(index, title, message);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MailCommand.MESSAGE_USAGE));
        }
    }
}
