package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.ShellIOException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandUtilTest {

    @Test
    public void testCatCommandUtil(){

        String command = "\"C:\\Users\\Asus\\Desktop\\FER and others\"HELLO";
        String[] arr = CommandUtil.directorySeparator(command);
        assertEquals("C:\\Users\\Asus\\Desktop\\FER and others",arr[0]);
        assertEquals("HELLO",arr[1]);


    }
    @Test
    public void testCommandThrows(){

        String command = "dda das d";
        assertThrows(ShellIOException.class, () -> CommandUtil.directorySeparator(command));

    }


}
