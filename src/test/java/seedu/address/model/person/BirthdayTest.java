package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BirthdayTest {

    @Test
    public void isValidPhone() {
        // invalid birthdays
        assertFalse(Phone.isValidPhone("")); // empty string
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("91")); // less than 3 numbers
        assertFalse(Phone.isValidPhone("12011997")); // no forward slashes between digits
        assertFalse(Phone.isValidPhone("twelve/dec/nineteen-ninety")); // non-numeric
        assertFalse(Phone.isValidPhone("90/dec/1997")); // alphabets within digits
        assertFalse(Phone.isValidPhone("93/ 15/ 34")); // spaces within digits

        // valid birthday
        assertTrue(Phone.isValidPhone("20/11/1997")); // dd/mm/yyyy
        assertTrue(Phone.isValidPhone("20/11/97")); //dd/mm/yy
    }
}
