package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAIL_MESSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAIL_RECEPIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAIL_TITLE;

import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.MailCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AnyParticularContainsKeywordsPredicate;

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

        if (!arePrefixesPresent(argMultimap, PREFIX_MAIL_RECEPIENT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MailCommand.MESSAGE_USAGE));
        }

        try {
            String[] mailToCommand = ParserUtil.parseMailToCommand(argMultimap.getAllValues(PREFIX_MAIL_RECEPIENT));
            String title = String.join("", argMultimap.getAllValues(PREFIX_MAIL_TITLE));
            String message = String.join("", argMultimap.getAllValues(PREFIX_MAIL_MESSAGE));

            return new MailCommand(new AnyParticularContainsKeywordsPredicate(Arrays.asList(mailToCommand)),
                    title, message);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MailCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
