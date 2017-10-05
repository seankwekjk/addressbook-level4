package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

import static java.util.Objects.requireNonNull;

/**
 *Represents a Remark in the AddressBook.
 * Guarantees: immutable; name is valid as declared in {@link #isValidRemark(String)}
 */

public class Remark {
    public static final String MESSAGE_REMARK_CONSTRAINTS = "Remarks should be alphanumeric";
    public static final String REMARK_VALIDATION_REGEX = "\\p{Alnum}+";

    public final String remarkText;

    /**
     * Validates given Remark.
     *
     * @throws IllegalValueException if the given remark string is invalid.
     */
    public Remark(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!isValidRemark(trimmedName)) {
            throw new IllegalValueException(MESSAGE_REMARK_CONSTRAINTS);
        }
        this.remarkText = trimmedName;
    }

    /**
     * Returns true if a given string is a valid remark.
     */
    public static boolean isValidRemark(String test) {
        return test.matches(REMARK_VALIDATION_REGEX);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + remarkText + ']';
    }
}
