package seedu.address.logic.parser;

import java.util.Arrays;
import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.MailCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AnyParticularContainsKeywordsPredicate;

public class MailCommandParser implements Parser<MailCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the MailCommand
     * and returns an MailCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MailCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, MAIL_RECEPIENT, MAIL_TITLE, MAIL_MESSAGE);

        if (!arePrefixesPresent(argMultimap, MAIL_RECEPIENT) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MailCommand.MESSAGE_USAGE));
        }

        try {
            String[] createList = ParserUtil.parseMailToCommand(argMultimap.getAllValues(MAIL_RECEPIENT));
            String title = String.join(" ", argMultimap.getAllValues(MAIL_TITLE)).replace(" ", "%2C");
            String message = String.join(" ", argMultimap.getAllValues(MAIL_SUBJECT)).replace(" ", "%2C");
        }
        catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new MailCommand(new AnyParticularContainsKeywordsPredicate(Arrays.asList(createList)), title, message);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
