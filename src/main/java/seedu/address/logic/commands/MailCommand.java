package seedu.address.logic.commands;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.AnyParticularContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.logic.parser.exceptions.ParseException;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAIL_RECEPIENT;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_MAIL_TITLE;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_MAIL_MESSAGE;

/**
 * Mails a person in Contags.
 */
public class MailCommand extends Command {

    private final AnyParticularContainsKeywordsPredicate targetIndex;
    //private final String title;
    //private final String message;

    public MailCommand(AnyParticularContainsKeywordsPredicate targetIndex) {
        this.targetIndex = targetIndex;
        //this.title = title;
        //this.message = message;
    }

    public static final String COMMAND_WORD = "mail";
    public static final String MESSAGE_SUCCESS = "Redirect to Mail application success.";
    public static final String MESSAGE_FAILURE = "Could not redirect to Mail application. Please enter a valid mail address.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Mails a contact in Contags.\n"
            + "Recepient mail address cannot be blank.\n"
            + "Parameters: " + PREFIX_MAIL_RECEPIENT + " NAME\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_MAIL_RECEPIENT + "John Doe";

    /**
     * Opens up Desktop Mail application.
     */

    private void sendMail(String sendMailTo) throws ParseException {
        if (sendMailTo.equalsIgnoreCase(" ")) {
            throw new ParseException("Recipient mail is not valid. Please enter a valid mail address.");
        }

        Desktop desktop = Desktop.getDesktop();
        URI mailTo;
        String url = "";

        try {
            url = "mailTo:" + sendMailTo;
            mailTo = new URI(url);
            desktop.mail(mailTo);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void send(String from, Collection<String> recipients, String subject, String text)
            throws MessagingException, IOException {
        send(from, recipients, subject, text, null, null, null);

        Properties properties = new Properties();
        properties.load(JavaMailService.class.getResourceAsStream("/mail.properties"));

        // create a Session instance specifying the system properties
        Session session = Session.getInstance(properties);

        // create a message instance associated to the session
        MimeMessage message = new MimeMessage(session);


        // configure from address, add recipients, and set the subject of the message
        message.setFrom(from);
        message.addRecipients(Message.RecipientType.TO, String.join(",", recipients));
        message.setSubject(subject);

        // send the message
        String username = properties.getProperty("mail.smtp.username");
        String password = properties.getProperty("mail.smtp.password");
        Transport.send(message, username, password);
    }

    @Override
    public CommandResult execute() {
        String sendMailTo = model.updateMailRecipientList(targetIndex);
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
