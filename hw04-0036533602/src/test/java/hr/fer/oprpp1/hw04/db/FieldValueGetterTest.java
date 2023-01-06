package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FieldValueGetterTest {

    @Test
    public void testJMBAG(){
        StudentRecord studentRecord = new StudentRecord(
                "0036512345",
                "Dean",
                "Trkulja",
                4
        );

        IFieldValueGetter getJMBAGName = FieldValueGetters.JMBAG;

        assertEquals("0036512345",getJMBAGName.get(studentRecord));
    }

    @Test
    public void testFirstName(){
        StudentRecord studentRecord = new StudentRecord(
                "0036512345",
                "Dean",
                "Trkulja",
                4
        );

        IFieldValueGetter getFirstName = FieldValueGetters.FIRST_NAME;

        assertEquals("Dean",getFirstName.get(studentRecord));
    }

    @Test
    public void testLastName(){
        StudentRecord studentRecord = new StudentRecord(
                "0036512345",
                "Dean",
                "Trkulja",
                4
        );

        IFieldValueGetter getLastName = FieldValueGetters.LAST_NAME;

        assertEquals("Trkulja",getLastName.get(studentRecord));
    }


}
