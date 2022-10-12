package hr.fer.oprpp1.custom.collections;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

public class LinkedListIndexedCollectionTest {
    private LinkedListIndexedCollection emptyLinkedList;


    @BeforeEach
    public void setup(){
        emptyLinkedList = new LinkedListIndexedCollection();
        emptyLinkedList.add("Test1");
        emptyLinkedList.add("Test2");
        emptyLinkedList.add("Test3");



    }

    @Test
    public void testDefaultConstructor(){
        LinkedListIndexedCollection list = new LinkedListIndexedCollection();
        assertEquals(0, list.size());
    }

    @Test
    public void testSecondConstructor(){
        LinkedListIndexedCollection list = new LinkedListIndexedCollection(emptyLinkedList);
        assertEquals(emptyLinkedList.size(),list.size());
    }

    @Test
    public void testAddingElement(){
        emptyLinkedList.add("Test4");
        assertEquals("Test4", emptyLinkedList.get(emptyLinkedList.size() - 1));
    }

    @Test
    public void testInsertingElementInFirstPlace(){
        emptyLinkedList.insert("Test4", 0);
        assertEquals("Test4", emptyLinkedList.get(0));
    }

    @Test
    public void testInsertingElementInBetween(){
        emptyLinkedList.insert("Test4", 1);
        assertEquals("Test4", emptyLinkedList.get(1));
    }

    @Test
    public void testInsertingElementAtEnd(){
        emptyLinkedList.insert("Test4", emptyLinkedList.size());
        assertEquals("Test4", emptyLinkedList.get( emptyLinkedList.size() - 1));
    }

    @Test
    public void testInsertingWithInvalidPosition(){
        assertThrows(IllegalArgumentException.class, () -> emptyLinkedList.insert("Test4", emptyLinkedList.size() + 10));
    }

    @Test
    public void testInsertingNullValue(){
        assertThrows(NullPointerException.class, () -> emptyLinkedList.insert(null,0));
    }

    @Test
    public void testGettingElementWithValidArgument(){
        assertEquals("Test1",emptyLinkedList.get(0));
    }

    @Test
    public void testGettingElementWithInvalidArgument(){
        assertThrows(IndexOutOfBoundsException.class,() ->emptyLinkedList.get(emptyLinkedList.size() + 2));
    }

    @Test
    public void testGettingIndexOfNullValue(){
        assertEquals(-1, emptyLinkedList.indexOf(null));
    }

    @Test
    public void testGettingIndexOfNonExistingValue(){
        assertEquals(-1, emptyLinkedList.indexOf("dada"));
    }

    @Test
    public void testGettingIndexOfExistingValue(){
        assertEquals(0, emptyLinkedList.indexOf("Test1"));
    }

    @Test
    public void testRemoveWithInvalidIndex(){
        assertThrows(IllegalArgumentException.class, () -> emptyLinkedList.remove(emptyLinkedList.size() + 3));
    }
    @Test
    public void testRemoveFirstElementWithIndex(){
        emptyLinkedList.remove(0);
        assertEquals("Test2", emptyLinkedList.get(0));
    }

    @Test
    public void testRemoveMiddleElementWithIndex(){
        emptyLinkedList.remove(1);
        assertEquals("Test3", emptyLinkedList.get(1));
    }

    @Test
    public void testRemoveLastElementWithIndex(){
        emptyLinkedList.remove(emptyLinkedList.size() - 1);
        assertEquals("Test2", emptyLinkedList.get(emptyLinkedList.size() - 1));
    }

    @Test
    public void testContainsTrue(){
        assertTrue(emptyLinkedList.contains("Test1"));
    }

    @Test
    public void testContainsFalse(){
        assertFalse(emptyLinkedList.contains("TestTest"));
    }

    @Test
    public void testToArray(){
        Object[] arr = new Object[] {"Test1", "Test2", "Test3"};
        assertEquals(arr[0],emptyLinkedList.toArray()[0]);
    }
    @Test
    public void testClear(){
        emptyLinkedList.clear();
        assertEquals(0,emptyLinkedList.size());
    }

    @Test
    public void testSize() {
        int size = emptyLinkedList.size() + 1;
        emptyLinkedList.add("Test4");
        assertEquals(size, emptyLinkedList.size());
    }


}
