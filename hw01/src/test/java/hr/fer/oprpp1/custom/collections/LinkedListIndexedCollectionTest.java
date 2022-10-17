package hr.fer.oprpp1.custom.collections;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for LinkedListCollection class
 */

public class LinkedListIndexedCollectionTest {
    /**
     * Variable which will be used for testing
     */
    private LinkedListIndexedCollection emptyLinkedList;


    /**
     * This function is called before every test so that we have an instance of Linked list with some data
     */
    @BeforeEach
    public void setup(){
        emptyLinkedList = new LinkedListIndexedCollection();
        emptyLinkedList.add("Test1");
        emptyLinkedList.add("Test2");
        emptyLinkedList.add("Test3");
    }

    /**
     * Testing default constructor
     */

    @Test
    public void testDefaultConstructor(){
        LinkedListIndexedCollection list = new LinkedListIndexedCollection();
        assertEquals(0, list.size());
    }

    /**
     * Testing constructor that has another colection as parameter
     */
    @Test
    public void testSecondConstructor(){
        LinkedListIndexedCollection list = new LinkedListIndexedCollection(emptyLinkedList);
        assertEquals(emptyLinkedList.size(),list.size());
    }

    /**
     * Testing add method
     */

    @Test
    public void testAddingElement(){
        emptyLinkedList.add("Test4");
        assertEquals("Test4", emptyLinkedList.get(emptyLinkedList.size() - 1));
    }

    /**
     * Testing inserting value in first place in list
     */

    @Test
    public void testInsertingElementInFirstPlace(){
        emptyLinkedList.insert("Test4", 0);
        assertEquals("Test4", emptyLinkedList.get(0));
    }

    /**
     * Testing inserting element between 2 nodes
     */

    @Test
    public void testInsertingElementInBetween(){
        emptyLinkedList.insert("Test4", 1);
        assertEquals("Test4", emptyLinkedList.get(1));
    }

    /**
     * Testing inserting element at the end of list
     */
    @Test
    public void testInsertingElementAtEnd(){
        emptyLinkedList.insert("Test4", emptyLinkedList.size());
        assertEquals("Test4", emptyLinkedList.get( emptyLinkedList.size() - 1));
    }

    /**
     * Testing inserting value on invalid position
     */

    @Test
    public void testInsertingWithInvalidPosition(){
        assertThrows(IllegalArgumentException.class, () -> emptyLinkedList.insert("Test4", emptyLinkedList.size() + 10));
    }

    /**
     * Testing inserting a null value
     */

    @Test
    public void testInsertingNullValue(){
        assertThrows(NullPointerException.class, () -> emptyLinkedList.insert(null,0));
    }

    /**
     * Testing getting an element from list
     */
    @Test
    public void testGettingElementWithValidArgument(){
        assertEquals("Test1",emptyLinkedList.get(0));
    }
    /**
     * Testing getting an element from list with invalid position
     */
    @Test
    public void testGettingElementWithInvalidArgument(){
        assertThrows(IndexOutOfBoundsException.class,() ->emptyLinkedList.get(emptyLinkedList.size() + 2));
    }

    /**
     * Testing getting an index of an element with null value
     */

    @Test
    public void testGettingIndexOfNullValue(){
        assertEquals(-1, emptyLinkedList.indexOf(null));
    }

    /**
     * Testing getting an index of an element with value that doesn't exists
     */

    @Test
    public void testGettingIndexOfNonExistingValue(){
        assertEquals(-1, emptyLinkedList.indexOf("dada"));
    }

    /**
     *   Testing getting an index of an element with value that exists
     */

    @Test
    public void testGettingIndexOfExistingValue(){
        assertEquals(0, emptyLinkedList.indexOf("Test1"));
    }

    /**
     * Testing removing node with invalid position as argument
     */

    @Test
    public void testRemoveWithInvalidIndex(){
        assertThrows(IllegalArgumentException.class, () -> emptyLinkedList.remove(emptyLinkedList.size() + 3));
    }

    /**
     * Testing removing first node
     */
    @Test
    public void testRemoveFirstElementWithIndex(){
        emptyLinkedList.remove(0);
        assertEquals("Test2", emptyLinkedList.get(0));
    }

    /**
     * Testing removing node between 2 nodes
     */

    @Test
    public void testRemoveMiddleElementWithIndex(){
        emptyLinkedList.remove(1);
        assertEquals("Test3", emptyLinkedList.get(1));
    }

    /**
     * Testing removing last node
     */

    @Test
    public void testRemoveLastElementWithIndex(){
        emptyLinkedList.remove(emptyLinkedList.size() - 1);
        assertEquals("Test2", emptyLinkedList.get(emptyLinkedList.size() - 1));
    }

    /**
     * Testing if returns false if we want to remove null value
     */
    @Test
    public void testRemoveElementWithNullValue(){
        assertFalse(emptyLinkedList.remove(null));
    }

    /**
     * Testing if returns false if we want to remove element with value that doesn't exists
     */
    @Test
    public void testRemoveElementWithNoExistingValue(){
        assertFalse(emptyLinkedList.remove("this doesn exists"));
    }

    /**
     * Testing if returns true if we want to remove elemet with value that exists
     */
    @Test
    public void testRemoveExistingElement(){
        assertTrue(emptyLinkedList.remove("Test1"));
    }

    /**
     * Testing if it returns true if value exists in list
     */

    @Test
    public void testContainsTrue(){
        assertTrue(emptyLinkedList.contains("Test1"));
    }

    /**
     * Testing if it returns false if value doesn't exists in list
     */

    @Test
    public void testContainsFalse(){
        assertFalse(emptyLinkedList.contains("TestTest"));
    }

    /**
     * Testing if an array is returned
     */
    @Test
    public void testToArray(){
        Object[] arr = new Object[] {"Test1", "Test2", "Test3"};
        assertArrayEquals(arr,emptyLinkedList.toArray());
    }

    /**
     * Testing if list get cleared
     */
    @Test
    public void testClear(){
        emptyLinkedList.clear();
        assertEquals(0,emptyLinkedList.size());
    }

    /**
     * Testing size of list
     */

    @Test
    public void testSize() {
        int size = emptyLinkedList.size() + 1;
        emptyLinkedList.add("Test4");
        assertEquals(size, emptyLinkedList.size());
    }


}
