package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 * Test class for ArrayIndexedCollection class
 */

public class ArrayIndexedCollectionTest {
    /**
     * Variable which will be used for testing
     */
    private ArrayIndexedCollection arrayWithNoParams;

    /**
     * This function is called before every test so that we have an instance of Linked list with some data
     */

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

    /**
     * Testing default constructor
     */
    @Test
    public void testConstructorWithNoParam(){
        ArrayIndexedCollection arr = new ArrayIndexedCollection();
        assertEquals(0,arr.size());
    }

    /**
     * Testing constructor with param initialCapacity
     */
    @Test
    public void testConstructorWithInitSize(){
        ArrayIndexedCollection arr = new ArrayIndexedCollection(12);
        assertEquals(0,arr.size());
    }

    /**
     * Testing constructor that has other collection as param
     */
    @Test
    public void testConstructorWithColl(){
        ArrayIndexedCollection arr = new ArrayIndexedCollection(arrayWithNoParams);
        assertEquals("Test1",arr.get(0));
    }

    /**
     * Testing constructor that has other collection and initialCapacity as param
     */
    @Test
    public void testConstructorWithCollAndInitSize(){
        ArrayIndexedCollection arr = new ArrayIndexedCollection(arrayWithNoParams,1);
        assertEquals("Test1",arr.get(0));
    }

    /**
     * Testing inserting element in invalid position (< 0)
     */

    @Test
    public void testInsertWithInvalidPosition(){
        assertThrows(IndexOutOfBoundsException.class, () -> arrayWithNoParams.insert("Test3", -1));
    }

    /**
     * Testing inserting element in invalid position (> size)
     */
    @Test
    public void testInsertWithInvalidPosition2(){
        assertThrows(IndexOutOfBoundsException.class, () -> arrayWithNoParams.insert("Something", arrayWithNoParams.size()+1));
    }

    /**
     * Testing inserting null element
     */
    @Test
    public void testInsertWithNullValue(){
        assertThrows(NullPointerException.class, () -> arrayWithNoParams.insert(null, 1));
    }

    /**
     * Testing inserting in first place
     */
    @Test
    public void testInsertIntoFirstPosition(){
        arrayWithNoParams.insert("Test3", 0);
        assertEquals("Test3", arrayWithNoParams.get(0));
    }

    /**
     * Testing inserting an element between 2 elements
     */

    @Test
    public void testInsertInMiddle(){
        arrayWithNoParams.insert("Test3",1);
        assertEquals("Test3", arrayWithNoParams.get(1));
    }

    /**
     * Testing inserting an element at the end
     */

    @Test
    public void testInsertAtEnd(){
        arrayWithNoParams.insert("Test3",arrayWithNoParams.size());
        assertEquals("Test3", arrayWithNoParams.get(arrayWithNoParams.size()-1));
    }

    /**
     * Testing getting a value with invalid position(< 0)
     */
    @Test
    public void testGetWithInvalidPosition(){
        assertThrows(IndexOutOfBoundsException.class, () -> arrayWithNoParams.get(-1));
    }

    /**
     * Testing getting a value with invalid position(> size)
     */

    @Test
    public void testGetWithInvalidPosition2(){
        assertThrows(IndexOutOfBoundsException.class, () -> arrayWithNoParams.get(arrayWithNoParams.size()));
    }

    /**
     * Testing getting a value with valid position
     */
    @Test
    public void testGetWithValidArgument(){
        assertEquals("Test1", arrayWithNoParams.get(0));
    }

    /**
     * Testing removing a value with invalid position(< 0)
     */
    @Test
    public void testRemoveWithInvalidArgument(){
        assertThrows(IllegalArgumentException.class,() -> arrayWithNoParams.remove(-1));
    }

    /**
     * Testing removing a value with invalid position(> size)
     */

    @Test
    public void testRemoveWithInvalidArgument2(){
        assertThrows(IllegalArgumentException.class,() -> arrayWithNoParams.remove(arrayWithNoParams.size()));
    }

    /**
     * Testing removing first element
     */
    @Test
    public void testRemoveFirstElement(){
        arrayWithNoParams.remove(0);
        assertEquals("Test2", arrayWithNoParams.get(0));
    }

    /**
     * Testing removing an element between 2 other elements
     */
    @Test
    public void testRemoveMiddleElement(){
        arrayWithNoParams.add("Test3");
        arrayWithNoParams.remove(1);
        assertEquals("Test3", arrayWithNoParams.get(1));
    }

    /**
     * Testing removing last element
     */

    @Test
    public void testRemoveLastElement(){
        arrayWithNoParams.remove(1);
        assertEquals("Test1", arrayWithNoParams.get(0));
    }

    /**
     * Testing if returns valid size of an collection
     */

    @Test
    public void testSize(){
        assertEquals(2, arrayWithNoParams.size());
    }

    /**
     * Testing if it returns false if value doesn't exists in list
     */
    @Test
    public void testContainsFalse(){
        assertFalse(arrayWithNoParams.contains("Test"));
    }

    /**
     * Testing if it returns true if value exists in list
     */
    @Test
    public void testContainsTrue(){
        assertTrue(arrayWithNoParams.contains("Test1"));
    }

    /**
     * Testing removing element when null is passed as argument
     */

    @Test
    public void testRemoveWithObjectNull(){
        assertFalse(arrayWithNoParams.remove(null));
    }

    /**
     * Testing removing first element
     */

    @Test
    public void testRemoveWithObjectFirst(){
        arrayWithNoParams.remove("Test1");
        assertEquals("Test2", arrayWithNoParams.get(0));
    }

    /**
     * Testing removing middle element
     */

    @Test
    public void testRemoveWithObjectMiddle(){
        arrayWithNoParams.insert("Test3",arrayWithNoParams.size());
        arrayWithNoParams.remove("Test2");
        assertEquals("Test3", arrayWithNoParams.get(arrayWithNoParams.size() -1));
    }

    /**
     * Testing removing last element
     */
    @Test
    public void testRemoveWithObjectLast(){
        arrayWithNoParams.insert("Test3",1);
        arrayWithNoParams.remove("Test2");
        assertEquals("Test3", arrayWithNoParams.get(arrayWithNoParams.size() - 1));
    }

    /**
     * Testing if collection gets empty
     */
    @Test
    public void testClear(){
        arrayWithNoParams.clear();
        assertEquals(0,arrayWithNoParams.size());
    }

    /**
     * Testing getting an index of an element with null value
     */
    @Test
    public void testIndexOfNull(){
        assertEquals(-1, arrayWithNoParams.indexOf(null));
    }

    /**
     * Testing getting an index of an element with value that doesn't exists
     */

    @Test
    public void testIndexOfNonExistingElement(){
        assertEquals(-1, arrayWithNoParams.indexOf("Tst"));
    }

    /**
     *   Testing getting an index of an element with value that exists
     */

    @Test
    public void testIndexOfNullExistingElement(){
        assertEquals(0, arrayWithNoParams.indexOf("Test1"));
    }

    /**
     * Testing if an array is returned
     */

    @Test
    public void testToArray(){
        Object[] arr = new Object[] {"Test1", "Test2"};
        assertArrayEquals(arr, arrayWithNoParams.toArray());
    }

    /**
     * Testing if it will store null value
     */

    @Test
    public void testAddingNullValue(){
        assertThrows(NullPointerException.class, () -> arrayWithNoParams.add(null));
    }

    /**
     * Testing adding a new value
     */

    @Test
    public void testAddingValue(){
        arrayWithNoParams.add("Test3");
        assertEquals("Test3", arrayWithNoParams.get(arrayWithNoParams.size() - 1));
    }


}
