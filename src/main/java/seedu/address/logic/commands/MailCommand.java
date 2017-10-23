package seedu.address.logic.commands;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.AnyParticularContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Mails a person in Contags.
 */
public class MailCommand extends Command {

    public static final String COMMAND_WORD = "mail";
    public static final String MESSAGE_SUCCESS = "Mail successfully sent.";
    public static final String MESSAGE_FAILURE = "Mail wasn't sent. Please enter a valid mail address and valid message.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Mails a contact in Contags.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + MAIL_RECEPIENT + " John Doe "
            + MAIL_TITLE + " Meeting Reminder "
            + MAIL_MESSAGE + " Meeting is at 2pm.\n";

    private final AnyParticularContainsKeywordsPredicate targetIndex;
    private final String title;
    private final String message;

    public MailCommand(AnyParticularContainsKeywordsPredicate targetIndex, String title, String message) {
        this.targetIndex = targetIndex;
        this.title = title;
        this.message = message;
    }

    private void sendMail(String sendMailTo) throws ParseException {
        if (sendMailTo.equalsIgnoreCase(" ")) {
            throw new ParseException("Recipient mail is not valid. Please enter a valid mail address.");
        }

        Desktop desktop = Desktop.getDesktop();
        URI mailRecepient;
        String url = "";

        try {
            url = "mailRecepient:" + sendMailTo + "&body=";
            mailRecepient = new URI(url);
            desktop.mail(mailRecepient);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CommandResult execute() {
        try {
            sendMail(sendMailTo);
        }
        catch (ParseException e) {
            e.printStackTrace();
            return new CommandResult(MESSAGE_FAILURE);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MailCommand // instanceof handles nulls
                && this.targetIndex.equals(((MailCommand) other).targetIndex)); // state check
    }

}
