package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class ArrayIndexedCollectionTest {
    private ArrayIndexedCollection arrayWithNoParams;


    @BeforeEach
    void setUp(){
        Random random = new Random();
        arrayWithNoParams = new ArrayIndexedCollection();
        ArrayIndexedCollection arrayWithInitSize = new ArrayIndexedCollection(8);

        arrayWithNoParams.add("Test1");
        arrayWithNoParams.add("Test2");

        for (int i = 0; i < 10; i++){
            arrayWithInitSize.add(random.nextInt(20));
        }

        ArrayIndexedCollection arrayWithCollInit = new ArrayIndexedCollection(arrayWithInitSize);

    }
    @Test
    public void testConstructorWithNoParam(){
        ArrayIndexedCollection arr = new ArrayIndexedCollection();
        assertEquals(0,arr.size());
    }
    @Test
    public void testConstructorWithInitSize(){
        ArrayIndexedCollection arr = new ArrayIndexedCollection(12);
        assertEquals(0,arr.size());
    }
    @Test
    public void testConstructorWithColl(){
        ArrayIndexedCollection arr = new ArrayIndexedCollection(arrayWithNoParams);
        assertEquals("Test1",arr.get(0));
    }
    @Test
    public void testConstructorWithCollAndInitSize(){
        ArrayIndexedCollection arr = new ArrayIndexedCollection(arrayWithNoParams,1);
        assertEquals("Test1",arr.get(0));
    }

    @Test
    public void testInsertWithInvalidPosition(){
        assertThrows(IndexOutOfBoundsException.class, () -> arrayWithNoParams.insert("Test3", -1));
    }

    @Test
    public void testInsertWithInvalidPosition2(){
        assertThrows(IndexOutOfBoundsException.class, () -> arrayWithNoParams.insert("Something", arrayWithNoParams.size()+1));
    }
    @Test
    public void testInsertWithNullValue(){
        assertThrows(NullPointerException.class, () -> arrayWithNoParams.insert(null, 1));
    }

    @Test
    public void testInsertIntoFirstPosition(){
        arrayWithNoParams.insert("Test3", 0);
        assertEquals("Test3", arrayWithNoParams.get(0));
    }

    @Test
    public void testInsertInMiddle(){
        arrayWithNoParams.insert("Test3",1);
        assertEquals("Test3", arrayWithNoParams.get(1));
    }

    @Test
    public void testInsertAtEnd(){
        arrayWithNoParams.insert("Test3",arrayWithNoParams.size());
        assertEquals("Test3", arrayWithNoParams.get(arrayWithNoParams.size()-1));
    }

    @Test
    public void testGetWithInvalidPosition(){
        assertThrows(IndexOutOfBoundsException.class, () -> arrayWithNoParams.get(-1));
    }

    @Test
    public void testGetWithInvalidPosition2(){
        assertThrows(IndexOutOfBoundsException.class, () -> arrayWithNoParams.get(arrayWithNoParams.size()));
    }

    @Test
    public void testGetWithValidArgument(){
        assertEquals("Test1", arrayWithNoParams.get(0));
    }

    @Test
    public void testRemoveWithInvalidArgument(){
        assertThrows(IllegalArgumentException.class,() -> arrayWithNoParams.remove(-1));
    }

    @Test
    public void testRemoveWithInvalidArgument2(){
        assertThrows(IllegalArgumentException.class,() -> arrayWithNoParams.remove(arrayWithNoParams.size()));
    }

    @Test
    public void testRemoveFirstElement(){
        arrayWithNoParams.remove(0);
        assertEquals("Test2", arrayWithNoParams.get(0));
    }

    @Test
    public void testRemoveMiddleElement(){
        arrayWithNoParams.add("Test3");
        arrayWithNoParams.remove(1);
        assertEquals("Test3", arrayWithNoParams.get(1));
    }

    @Test
    public void testRemoveLastElement(){
        arrayWithNoParams.remove(1);
        assertEquals("Test1", arrayWithNoParams.get(0));
    }

    @Test
    public void testSize(){
        assertEquals(2, arrayWithNoParams.size());
    }

    @Test
    public void testContainsFalse(){
        assertFalse(arrayWithNoParams.contains("Test"));
    }

    @Test
    public void testContainsTrue(){
        assertTrue(arrayWithNoParams.contains("Test1"));
    }

    @Test
    public void testRemoveWithObjectNull(){
        assertFalse(arrayWithNoParams.remove(null));
    }

    @Test
    public void testRemoveWithObjectFirst(){
        arrayWithNoParams.remove("Test1");
        assertEquals("Test2", arrayWithNoParams.get(0));
    }

    @Test
    public void testRemoveWithObjectMiddle(){
        arrayWithNoParams.insert("Test3",arrayWithNoParams.size());
        arrayWithNoParams.remove("Test2");
        assertEquals("Test3", arrayWithNoParams.get(arrayWithNoParams.size() -1));
    }

    @Test
    public void testRemoveWithObjectLast(){
        arrayWithNoParams.insert("Test3",1);
        arrayWithNoParams.remove("Test2");
        assertEquals("Test3", arrayWithNoParams.get(arrayWithNoParams.size() - 1));
    }


    @Test
    public void testClear(){
        arrayWithNoParams.clear();
        assertEquals(0,arrayWithNoParams.size());
    }

    @Test
    public void testIndexOfNull(){
        assertEquals(-1, arrayWithNoParams.indexOf(null));
    }

    @Test
    public void testIndexOfNonExistingElement(){
        assertEquals(-1, arrayWithNoParams.indexOf("Tst"));
    }

    @Test
    public void testIndexOfNullExistingElement(){
        assertEquals(0, arrayWithNoParams.indexOf("Test1"));
    }

    @Test
    public void testToArray(){
        Object[] arr = new Object[] {"Test1", "Test2"};
        assertArrayEquals(arr, arrayWithNoParams.toArray());
    }

    @Test
    public void testAddingNullValue(){
        assertThrows(NullPointerException.class, () -> arrayWithNoParams.add(null));
    }

    @Test
    public void testAddingValue(){
        arrayWithNoParams.add("Test3");
        assertEquals("Test3", arrayWithNoParams.get(arrayWithNoParams.size() - 1));
    }









}
