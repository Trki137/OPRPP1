package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class ArrayIndexedCollectionTest {
    private Random random;
    private ArrayIndexedCollection arrayWithNoParams;
    private ArrayIndexedCollection arrayWithInitSize;
    private ArrayIndexedCollection arrayWithCollInit;

    private ArrayIndexedCollection arrayWithCollSizeInit;


    @BeforeEach
    void setUp(){
        random = new Random();
        arrayWithNoParams = new ArrayIndexedCollection();
        arrayWithInitSize = new ArrayIndexedCollection(8);

        arrayWithNoParams.add("Test1");
        arrayWithNoParams.add("Test2");

        for (int i = 0; i < 10; i++){
            arrayWithInitSize.add(random.nextInt(20-0) + 0);
        }

        arrayWithCollInit = new ArrayIndexedCollection(arrayWithInitSize);
        arrayWithCollSizeInit = new ArrayIndexedCollection(arrayWithCollInit, 3);

    }

    @Test
    public void testSize(){
        assertEquals(2, arrayWithNoParams.size());
    }
    @Test
    public void testAdding(){
        int expectedSize = arrayWithNoParams.size() + 2;
        arrayWithNoParams.add("Test3");
        arrayWithNoParams.add("Test4");
        assertEquals(expectedSize, arrayWithNoParams.size());
    }
    @Test
    public void testGettingElementByIndex(){
        assertEquals("Test2", arrayWithNoParams.get(1));
    }
    @Test
    public void testIndexOfMethod(){
        assertEquals(0, arrayWithNoParams.indexOf("Test1"));
    }
    @Test
    public void testRemove() {
        arrayWithNoParams.remove(0);
        assertEquals(1,arrayWithNoParams.size());
    }
    @Test
    public void testContainsTrue(){
        assertTrue(arrayWithNoParams.contains("Test1"));
    }
    @Test
    public void testContainsFalse(){
        assertFalse(arrayWithNoParams.contains("TTTT"));
    }

    @Test
    public void testClear(){
        arrayWithInitSize.clear();
        assertEquals(0,arrayWithInitSize.size());
    }
    @Test
    public void testAddAllMethod(){
        int expectedSize = arrayWithNoParams.size() + arrayWithInitSize.size();
        arrayWithNoParams.addAll(arrayWithInitSize);
        assertEquals(expectedSize, arrayWithNoParams.size());
    }


    @Test
    public void testInsertInMiddle(){
        int expectedSize = arrayWithCollInit.size() + 1;
        arrayWithCollInit.insert("Test1", 2);
        assertEquals(expectedSize,arrayWithCollInit.size());
    }
    @Test
    public void testArraySizeComplexConstructor(){
        int expectedSize = arrayWithCollSizeInit.size() + 1;
        arrayWithCollSizeInit.add("Testovi");
        assertEquals(expectedSize, arrayWithCollSizeInit.size());
    }
}
