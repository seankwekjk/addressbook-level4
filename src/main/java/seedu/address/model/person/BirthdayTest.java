package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Represents a Person's birthday in the address book.
 */

public class BirthdayTest {
    @Test
    public void isValidBirthday() {
        //invalid birthdays
        assertFalse(Birthday.isValidBirthday("")); // empty string
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only
        assertFalse(Birthday.isValidBirthday("2001/2/11")); // not dd/mm/yyyy format
        assertFalse(Birthday.isValidBirthday("9011p041")); // alphabets within digit
        assertFalse(Birthday.isValidBirthday("birthday")); // non-numeric

        //valid birthdays
        assertTrue(Birthday.isValidBirthday("20/11/1997"));
        assertTrue(Birthday.isValidBirthday("02/09/1975"));
        assertTrue(Birthday.isValidBirthday("02/06/1970"));
    }

}
