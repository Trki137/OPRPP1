package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;


public class ArrayIndexedCollectionTest {
    private ArrayIndexedCollection<Integer> intArray;
    private ArrayIndexedCollection<String> stringArray;
    @BeforeEach
    public void setUp(){
        intArray = new ArrayIndexedCollection<>(6);
        stringArray = new ArrayIndexedCollection<>();

        intArray.add(1);
        intArray.add(2);
        intArray.add(3);

        stringArray.add("Dean");
        stringArray.add("Trkulja");
    }

    @Test
    public void testConstructorWithCollection(){
        assertInstanceOf(ArrayIndexedCollection.class,new ArrayIndexedCollection<>(intArray));
    }

    @Test
    public void testAddElement(){
        int added = 5;
        int size = intArray.size();
        intArray.add(added);
        assertEquals(size + 1, intArray.size());
        assertEquals(added, intArray.get(size));
    }

    @Test
    public void testAddMoreThanArrayLenght(){
        int exSize = intArray.size();
        for(int i = 0; i < 10; i++) {
            intArray.add(i * i);
            exSize++;
        }
        assertEquals(exSize,intArray.size());
    }

    @Test
    public void testToArray(){
        Object[] exArray =  new Object[]{1,2,3};
        assertArrayEquals(exArray,intArray.toArray());
    }

    @Test
    public void testRemoveWithIndexElement(){
        int size = intArray.size();
        int expElem = intArray.get(1);
        intArray.remove(0);
        assertEquals(size-1, intArray.size());
        assertEquals(expElem, intArray.get(0));
    }

    @Test
    public void testSize(){
        assertEquals(3, intArray.size());
    }

    @Test
    public void testGetElement(){
        String addedElem = "Zagreb";
        stringArray.add(addedElem);
        assertEquals(addedElem, stringArray.get(stringArray.size() - 1));
    }

    @Test
    public void testInsert(){
        int size = intArray.size();
        int addedElem = 6;
        intArray.insert(addedElem, 1);
        assertEquals(size + 1, intArray.size());
        assertEquals(addedElem,intArray.get(1));
    }

    @Test
    public void testIndexOf(){
        assertEquals(0,stringArray.indexOf("Dean"));
    }

    @Test
    public void testRemoveWithValue(){
        int size = stringArray.size();
        String expValue = stringArray.get(1);
        stringArray.remove("Dean");
        assertEquals(size - 1, stringArray.size());
        assertEquals(stringArray.get(0),expValue);
    }

    @Test
    public void testContains(){
        stringArray.add("Imsovac");
        assertTrue(stringArray.contains("Imsovac"));
        assertFalse(stringArray.contains("bla bla"));
    }

    @Test
    public void testClear(){
        stringArray.clear();
        assertEquals(0, stringArray.size());
        assertThrows(IndexOutOfBoundsException.class, () -> stringArray.get(0));
    }

    @Test
    public void testAddAllSatisfying(){
        ArrayIndexedCollection<Integer> newArrayInt = new ArrayIndexedCollection<>();
        for(int i = 1; i <= 5; i++) newArrayInt.add(i*i);
        Tester<Integer> tester = value -> value%2 == 0;
        intArray.addAllSatisfying(newArrayInt,tester);
        Integer[] arr = new Integer[]{1,2,3,4,16};
        assertEquals(5, intArray.size());
        for(int i = 0; i < intArray.size(); i++) assertEquals(arr[i], intArray.get(i));

    }

    @Test
    public void testElementsGetter(){
        ElementsGetter<Integer> elementsGetter = intArray.createElementsGetter();

        assertTrue(elementsGetter.hasNextElement());
        elementsGetter.getNextElement();
        assertEquals(2,elementsGetter.getNextElement());
        elementsGetter.getNextElement();
        assertFalse(elementsGetter.hasNextElement());
        assertThrows(NoSuchElementException.class, elementsGetter::getNextElement);
    }

    @Test
    public void testIsEmpty(){
        assertFalse(intArray.isEmpty());
        intArray.clear();
        assertTrue(intArray.isEmpty());
    }

}
