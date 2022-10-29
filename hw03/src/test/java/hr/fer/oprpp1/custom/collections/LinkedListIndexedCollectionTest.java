package hr.fer.oprpp1.custom.collections;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedListIndexedCollectionTest {
    private LinkedListIndexedCollection<Integer> intList;
    private LinkedListIndexedCollection<String> stringList;
    @BeforeEach
    public void setUp(){
        intList = new LinkedListIndexedCollection<>();
        stringList = new LinkedListIndexedCollection<>();

        intList.add(1);
        intList.add(2);
        intList.add(3);

        stringList.add("Dean");
        stringList.add("Trkulja");
    }

    @Test
    public void testConstructorWithCollection(){
        assertInstanceOf(LinkedListIndexedCollection.class,new LinkedListIndexedCollection<>(intList));
    }


    @Test
    public void testAddElement(){
        int added = 5;
        int size = intList.size();
        intList.add(added);
        assertEquals(size + 1, intList.size());
        assertEquals(added, intList.get(size));
    }

    @Test
    public void testRemoveWithIndexElement(){
        int size = intList.size();
        int expElem = intList.get(1);
        intList.remove(0);
        assertEquals(size-1, intList.size());
        assertEquals(expElem, intList.get(0));
    }

    @Test
    public void testSize(){
        assertEquals(3, intList.size());
    }

    @Test
    public void testGetElement(){
        String addedElem = "Zagreb";
        stringList.add(addedElem);
        assertEquals(addedElem, stringList.get(stringList.size() - 1));
    }

    @Test
    public void testInsertFirstPosition(){
        stringList.insert("Hello",0);
        assertEquals("Hello", stringList.get(0));
        assertTrue(stringList.contains("Hello"));

    }

    @Test
    public void testInsert(){
        int size = intList.size();
        int addedElem = 6;
        intList.insert(addedElem, 1);
        assertEquals(size + 1, intList.size());
        assertEquals(addedElem, intList.get(1));

        addedElem = 26;
        intList.insert(addedElem,1);
        assertEquals(size + 2, intList.size());
        assertEquals(addedElem, intList.get(1));
    }

    @Test
    public void testIndexOf(){
        assertEquals(0,stringList.indexOf("Dean"));
    }

    @Test
    public void testRemoveWithValue(){
        int size = stringList.size();
        String expValue = stringList.get(1);
        stringList.remove("Dean");
        assertEquals(size - 1, stringList.size());
        assertEquals(stringList.get(0),expValue);
    }

    @Test
    public void testRemoveLastElement(){
        String lastElement = stringList.get(stringList.size() - 1);
        stringList.remove(stringList.size() - 1);
        assertFalse(stringList.contains(lastElement));
    }
    @Test
    public void testRemoveMiddleElement(){
        String lastElement = stringList.get(stringList.size() - 1);
        stringList.add("Imsovac");
        stringList.remove(1);
        assertFalse(stringList.contains(lastElement));
    }

    @Test
    public void testToArray(){
        String[] exArr = new String[]{"Dean", "Trkulja"};
        assertArrayEquals(exArr,stringList.toArray());
    }



    @Test
    public void testContains(){
        stringList.add("Imsovac");
        assertTrue(stringList.contains("Imsovac"));
        assertFalse(stringList.contains("bla bla"));
    }

    @Test
    public void testClear(){
        stringList.clear();
        assertEquals(0, stringList.size());
        assertThrows(IndexOutOfBoundsException.class, () -> stringList.get(0));
    }

    @Test
    public void testAddAllSatisfying(){
        ArrayIndexedCollection<Integer> newArrayInt = new ArrayIndexedCollection<>();
        for(int i = 1; i <= 5; i++) newArrayInt.add(i*i);
        Tester<Integer> tester = value -> value%2 == 0;
        intList.addAllSatisfying(newArrayInt,tester);
        Integer[] arr = new Integer[]{1,2,3,4,16};
        assertEquals(5, intList.size());
        for(int i = 0; i < intList.size(); i++) assertEquals(arr[i], intList.get(i));

    }

    @Test
    public void testElementsGetter(){
        ElementsGetter<Integer> elementsGetter = intList.createElementsGetter();

        assertTrue(elementsGetter.hasNextElement());
        elementsGetter.getNextElement();
        assertEquals(2,elementsGetter.getNextElement());
        elementsGetter.getNextElement();
        assertFalse(elementsGetter.hasNextElement());
        assertThrows(NoSuchElementException.class, elementsGetter::getNextElement);
    }

    @Test
    public void testIsEmpty(){
        assertFalse(intList.isEmpty());
        intList.clear();
        assertTrue(intList.isEmpty());
    }
}
