package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

import static java.util.Objects.requireNonNull;

/**
 *Represents a Remark in the AddressBook.
 */

public class Remark {
    public final String remarkText;

    /**
     * Validates given Remark.
     *
     * @throws IllegalValueException if the given remark string is invalid.
     */
    public Remark(String name) throws IllegalValueException {
        requireNonNull(name);
        this.remarkText = name.trim();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + remarkText + ']';
    }
}
