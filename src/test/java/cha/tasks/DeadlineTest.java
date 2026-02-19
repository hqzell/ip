package cha.tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import cha.ChaException;

public class DeadlineTest {

    @Test
    public void parse_validInput_success() throws ChaException {
        Deadline d = Deadline.parse("hojicha /by 2019-10-15 1800");

        assertEquals("D", d.getType());
        assertEquals(
                LocalDateTime.of(2019, 10, 15, 18, 0),
                d.getBy()
        );
        assertTrue(d.toString().contains("15 Oct 2019"));
    }

    @Test
    public void parse_missingBy_throwsException() {
        ChaException e = assertThrows(
                ChaException.class,
                () -> Deadline.parse("hojicha")
        );

        assertEquals(
                "CHA doesn't know when it's due! (Use /by <time>)",
                e.getMessage()
        );
    }

    @Test
    public void parse_emptyDescription_throwsException() {
        ChaException e = assertThrows(
                ChaException.class,
                () -> Deadline.parse(" /by 2019-10-15 1800")
        );

        assertEquals(
                "CHA doesn't know what to do! (The description cannot be empty)",
                e.getMessage()
        );
    }

    @Test
    public void parse_invalidDateFormat_throwsException() {
        ChaException e = assertThrows(
                ChaException.class,
                () -> Deadline.parse("hojicha /by tomorrow")
        );

        assertEquals(
                "CHA can't understand that time! (Use format: yyyy-MM-dd HHmm)",
                e.getMessage()
        );
    }
}
