package seedu.address.logic.commands;

import java.awt.Desktop;
import java.io.IOException;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Adds a person to the address book.
 */
public class MailCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "mail";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a remark to a person in the address book. "

    private void sendEmail(String emailTo) throws ParseException {
        if (emailTo.equalsIgnoreCase("")) {
            throw new ParseException("Recipient mail is not valid. Please enter a valid mail address.");
        }
        if (emailTo.equalsIgnoreCase("/")) {
            throw new ParseException("Recipient mail is not valid. Please enter a valid mail address.");
        }
        if (emailTo.equalsIgnoreCase(".")) {
            throw new ParseException("Recipient mail is not valid. Please enter a valid mail address.");
        }
        if (emailTo.equalsIgnoreCase("\")) {
            throw new ParseException("Recipient mail is not valid. Please enter a valid mail address.");
        }

        Desktop desktop = Desktop.getDesktop();
        URI mailRecepient;
        String url = "";
        try {
            url = "mailTo:" + emailTo + "&body=";
            mailRecepient = new URI(url);
            desktop.mail(mailRecepient);
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MailCommand // instanceof handles nulls
                && this.targetIndex.equals(((MailCommand) other).targetIndex)); // state check
    }

}
