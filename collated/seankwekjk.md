# seankwekjk
###### \main\java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setSocial(String url) {
            this.url = url;
        }

        public Optional<String> getSocial() {
            return Optional.ofNullable(url);
        }

```
###### \main\java\seedu\address\logic\commands\RemarkCommand.java
``` java
/**
 * Adds, removes and edits remarks of an existing person in the address book.
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a remark to a person in the address book. "
            + "Parameters: "
            + "INDEX "
            + PREFIX_REMARK + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 " + PREFIX_REMARK
            + "Likes to drink coffee.";

    public static final String REMARK_EDIT_SUCCESS = "Remark added.";
    public static final String REMARK_CLEAR_SUCCESS = "Remark cleared.";

    private Remark remark;
    private Index index;

    /**
     * @param remark text of the remark
     * @param index of the person whose remark is being modified
     */
    public RemarkCommand(Remark remark, Index index) {
        this.remark = remark;
        this.index = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                                personToEdit.getAddress(), personToEdit.getBirthday(), remark, personToEdit.getTags());

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        return new CommandResult(successMessageType(editedPerson));
    }

    /**
     * Creates and returns a {@code String} based on the edited Remark of {@code personToEdit}
     */
    private String successMessageType(ReadOnlyPerson personToEdit) {
        if (!remark.getRemarkText().isEmpty()) {
            return String.format(REMARK_EDIT_SUCCESS, personToEdit);
        } else {
            return String.format(REMARK_CLEAR_SUCCESS, personToEdit);
        }
    }

    public Remark getRemark() {
        return remark;
    }

    public Index getIndex() {
        return index;
    }
}
```
###### \main\java\seedu\address\logic\commands\ToggleCommand.java
``` java
/**
 * Toggles Browser function of the Select Command.
 */
public class ToggleCommand extends Command {

    public static final String COMMAND_WORD = "toggle";

    public static final String TOGGLE_SUCCESS = "Browser Panel Toggled to ";

    @Override
    public CommandResult execute() throws CommandException {
        BrowserPanel.setBrowserMode();
        return new CommandResult(composeCommandResult());
    }

    /**
    * Composes the Command Result @String based on the status of the toggled boolean variable
    */
    private String composeCommandResult() {
        if (BrowserPanel.getBrowserMode()) {
            return TOGGLE_SUCCESS + "Display Address";
        }   else {
            return TOGGLE_SUCCESS + "Display Social Media";
        }
    }
}
```
###### \main\java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case RemarkCommand.COMMAND_WORD:
            return new RemarkCommandParser().parse(arguments);

        case ToggleCommand.COMMAND_WORD:
            return new ToggleCommand();

```
###### \main\java\seedu\address\logic\parser\RemarkCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RemarkCommand object
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {
    @Override
    public RemarkCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMARK);
        Index index;

        if (!arePrefixesPresent(argMultimap, PREFIX_REMARK)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                    RemarkCommand.MESSAGE_USAGE));
        }

        try {
            Remark remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK)).get();

            return new RemarkCommand(remark, index);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
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
```
###### \main\java\seedu\address\model\person\Person.java
``` java
    /**
     * Only url is allowed to be null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Birthday birthday,
                  Remark remark, String url, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, birthday, url, tags);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.birthday = new SimpleObjectProperty<>(birthday);
        this.remark = new SimpleObjectProperty<>(remark);
        this.social = new SimpleObjectProperty<>(url);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
    }

```
###### \main\java\seedu\address\model\person\Person.java
``` java
    public Remark getRemark() {
        return remark.get();
    }

    public ObjectProperty<Remark> remarkProperty() {
        return remark;
    }

    public void setRemark(Remark remark) {
        this.remark.set(requireNonNull(remark));
    }

    public String getSocialMedia() {
        return social.get();
    }

    public ObjectProperty<String> socialProperty() {
        return social;
    }

    public void setSocialMedia(String social) {
        this.social.set(requireNonNull(social));
    }

```
###### \main\java\seedu\address\model\person\Remark.java
``` java
/**
 *Represents a Remark in the AddressBook.
 */

public class Remark {
    private String remarkText;

    /**
     * Validates given Remark.
     */
    public Remark(String text) {
        try {
            this.remarkText = text.trim();
        } catch (NullPointerException npe) {
            remarkText = ("");
        }
    }

    /**
     * Returns remarkText for checking operations
     */
    public String getRemarkText() {
        return remarkText;
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + remarkText + ']';
    }
}
```
###### \main\java\seedu\address\ui\BrowserPanel.java
``` java
    private void loadPersonPage(ReadOnlyPerson pers) {
        loadPage(GOOGLE_SEARCH_URL_PREFIX
                + pers.getAddress().value.replaceAll(" ", "+").replaceAll(",", "%2C"));
    }

    private void loadSocialPage(ReadOnlyPerson pers) {
        loadPage(SOCIAL_MEDIA_URL_PREFIX + pers.getSocialMedia());
    }

    public static Boolean getBrowserMode() {
        return browserMode;
    }

    public static void setBrowserMode() {
        browserMode = !browserMode;
    }

```
###### \main\java\seedu\address\ui\BrowserPanel.java
``` java
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (browserMode) {
            loadPersonPage(event.getNewSelection().person);
        }   else {
            loadSocialPage(event.getNewSelection().person);
        }
    }
}
```
###### \test\java\seedu\address\logic\parser\RemarkCommandParserTest.java
``` java
/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class RemarkCommandParserTest {

    private static final String REMARK_TEXT = "text";

    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "0 " + PREFIX_REMARK + REMARK_TEXT,
                String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemarkCommand.MESSAGE_USAGE));
    }
}
```
###### \test\java\seedu\address\ui\BrowserPanelTest.java
``` java
        // associated address of a person
        postNow(selectionChangedEventStub);
        URL expectedPersonUrl = new URL(GOOGLE_SEARCH_URL_PREFIX
                + CARL.getAddress().value.replaceAll(" ", "+").replaceAll(",", "%2C"));

        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());

        // associated social media page of a person
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 1));
        BrowserPanel.setBrowserMode();
        postNow(selectionChangedEventStub);
        URL expectedSocialUrl = new URL(SOCIAL_MEDIA_URL_PREFIX + ALICE.getSocialMedia() + "/");

        assertEquals(expectedSocialUrl, browserPanelHandle.getLoadedUrl());
        BrowserPanel.setBrowserMode();
    }
}
```
###### \test\java\systemtests\RemarkCommandSystemTest.java
``` java
public class RemarkCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void remark() throws Exception {
        /* Case: follows expected syntax -> remark edited*/
        Index index = INDEX_FIRST_PERSON;
        String command = RemarkCommand.COMMAND_WORD + " " + index.getOneBased() + " " + REMARK_DESC_AMY;
        Remark remark = new Remark(VALID_REMARK_AMY);
        assertCommandSuccess(command, index, remark, RemarkCommand.REMARK_EDIT_SUCCESS);

        /* Case: clears remark -> remark edited*/
        command = RemarkCommand.COMMAND_WORD + " " + index.getOneBased() + " r/";
        Remark emptyRemark = new Remark("");
        assertCommandSuccess(command, index, emptyRemark, RemarkCommand.REMARK_CLEAR_SUCCESS);

        /* Case: filtered person list, edit index within bounds of address book but out of bounds of person list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        command = RemarkCommand.COMMAND_WORD + " " + invalidIndex + " " + REMARK_DESC_AMY;
        assertCommandFailure(command, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid index (0) -> rejected */
        command = RemarkCommand.COMMAND_WORD + " 0" + REMARK_DESC_AMY;
        assertCommandFailure(command, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Executes @code command
     * 1. Asserts that @code remark is equal to the updated remark @code toCheck
     * 2. Asserts that @code expectedMessage is the expected type of message result
     */
    private void assertCommandSuccess(String command, Index index, Remark remark, String expectedMessage) {
        executeCommand(command);
        ReadOnlyPerson personToCheck = getModel().getFilteredPersonList().get(index.getZeroBased());
        Remark toCheck = personToCheck.getRemark();
        assertTrue(remark.toString().equals(toCheck.toString()));
        assertEquals(expectedMessage, getResultDisplay().getText());
    }

    /**
     * Executes @code command
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
    }
}
```
###### \test\java\systemtests\ToggleCommandSystemTest.java
``` java
public class ToggleCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void toggle() throws Exception {
        /* Case: toggles to social media*/
        String command = ToggleCommand.COMMAND_WORD;
        String result = ToggleCommand.TOGGLE_SUCCESS + "Display Social Media";
        assertCommandSuccess(command, result);

        /* Case: toggles to maps*/
        command = ToggleCommand.COMMAND_WORD;
        result = ToggleCommand.TOGGLE_SUCCESS + "Display Address";
        assertCommandSuccess(command, result);
    }

    /**
     * Executes @code command
     * 1. Asserts that @code remark is equal to the updated remark @code toCheck
     * 2. Asserts that @code expectedMessage is the expected type of message result
     */
    private void assertCommandSuccess(String command, String expectedMessage) {
        executeCommand(command);
        assertEquals(expectedMessage, getResultDisplay().getText());
    }
}
```
