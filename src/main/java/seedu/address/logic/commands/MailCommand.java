package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_MAIL_MESSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAIL_RECEPIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAIL_TITLE;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Properties;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AnyParticularContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;


/**
 * Mails a person in Contags.
 */
public class MailCommand extends Command {

    public static final String COMMAND_WORD = "mail";
    public static final String COMMAND_ALIAS = "m";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Mails a contact in Contags.\n"
            + "Recepient mail address cannot be blank.\n"
            + "Parameters: " + PREFIX_MAIL_RECEPIENT + " INDEX" + PREFIX_MAIL_TITLE + " TITLE" + PREFIX_MAIL_MESSAGE
            + " MESSAGE\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_MAIL_RECEPIENT + "1" + " " + PREFIX_MAIL_TITLE
            + "Meeting Reminder" + " " + PREFIX_MAIL_MESSAGE + "Meeting is at 2pm on Sunday.";

    public static final String MESSAGE_SUCCESS = "Redirect to Mail application success.";
    public static final String MESSAGE_FAILURE = "Could not redirect to Mail application. "
            + "Please enter a valid mail address.";

    //private final AnyParticularContainsKeywordsPredicate targetIndex;
    private final Index targetIndex;
    private final String title;
    private final String message;

    public MailCommand(Index targetIndex, String title, String message) {
        this.targetIndex = targetIndex;
        this.title = title;
        this.message = message;
    }

    /**
     * Opens up Desktop Mail application.
     */

    private void sendMail(String sendMailTo) throws ParseException {
        if (sendMailTo.equalsIgnoreCase(" ")) {
            throw new ParseException("Recipient mail is not valid. Please enter a valid mail address.");
        }

        String host = "localhost";

        Properties properties = System.getProperties();
        properties.setProperty("mail.sftp.host", host);

        Desktop desktop = Desktop.getDesktop();
        URI mailTo;
        String url = "";

        try {
            url = "mailTo:" + sendMailTo + "?subject=" + this.title + "&body=" + this.message;;
            mailTo = new URI(url);
            desktop.mail(mailTo);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CommandResult execute() {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        String sendMailTo = lastShownList.get(targetIndex.getZeroBased()).getEmail().toString();

        try {
            sendMail(sendMailTo);
        } catch (ParseException e) {
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
