package hr.fer.oprpp1.hw05.crypto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UtilTest {

    @Test
    public void testHexToByte(){

        assertArrayEquals(new byte[]{1,34}, Util.hextobyte("0122"));
        assertArrayEquals(new byte[]{1,-82,34},Util.hextobyte("01aE22"));
        assertArrayEquals(new byte[]{-86,-1}, Util.hextobyte("aaff"));
        assertArrayEquals(new byte[]{69,35,-2}, Util.hextobyte("4523fe"));
        assertArrayEquals(new byte[]{102,17,-83}, Util.hextobyte("6611ad"));
        assertArrayEquals(new byte[]{-38,-50,34}, Util.hextobyte("dace22"));
    }

    @Test
    public void testByteToText(){
        assertEquals("01ae22", Util.bytetohex(new byte[] {1, -82, 34}));
        assertEquals("0122", Util.bytetohex(new byte[] {1, 34}));
        assertEquals("aaff", Util.bytetohex(new byte[] {-86, -1}));
        assertEquals("4523fe", Util.bytetohex(new byte[] {69, 35, -2}));
        assertEquals("6611ad", Util.bytetohex(new byte[] {102, 17, -83}));
        assertEquals("dace22", Util.bytetohex(new byte[] {-38, -50, 34}));
    }

    @Test
    public void testHexToByteOddStringThrows(){
        assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("012"));
    }

    @Test
    public void testHexToByteInvalidStringThrows(){
        assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("012G"));
    }
    @Test
    public void emptyByteArray(){
        assertEquals("",Util.bytetohex(new byte[]{}) );
    }
    @Test
    public void emptyKeyText(){
        assertArrayEquals(new byte[]{},Util.hextobyte(""));
    }
}
