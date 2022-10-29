package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleHashtableTest {
    SimpleHashtable<Integer,String> map;
    SimpleHashtable<Integer, String> mapWithElements;
    SimpleHashtable<String,Integer> mapWithStringKeys;

    @BeforeEach
    public void setUp(){
        map = new SimpleHashtable<>(2);
        mapWithElements = new SimpleHashtable<>(3);

        mapWithElements.put(1,"1");
        mapWithElements.put(2,"2");
        mapWithElements.put(3,"3");
        mapWithElements.put(4,"4");

        mapWithStringKeys = new SimpleHashtable<>(2);
        mapWithStringKeys.put("Ivana", 2);
        mapWithStringKeys.put("Ante", 2);
        mapWithStringKeys.put("Jasna", 2);
        mapWithStringKeys.put("Kristina", 5);
        mapWithStringKeys.put("Ivana", 5); // overwrites old grade for Ivana



    }

    @Test
    public void testDefaultConstructor(){
        assertInstanceOf(SimpleHashtable.class, new SimpleHashtable<>());
    }
    @Test
    public void testToString(){
        String exText = "[1=1 , 2=2 , 3=3 , 4=4]";
        assertEquals(exText,mapWithElements.toString());
    }

    @Test
    public void testInvalidSize(){
        assertThrows(IllegalArgumentException.class, () -> new SimpleHashtable<>(-3));
    }

    @Test
    public void testAddElementWithNullKey(){
        assertThrows(NullPointerException.class, () -> map.put(null, "Hello"));
    }

    @Test
    public void testAddNewElement(){
        map.put(1,"Dean");
        assertEquals(1, map.size());
        assertEquals("Dean", map.get(1));
        assertTrue(map.containsKey(1));
    }

    @Test
    public void testAddElementWithSameKey(){
        map.put(1,"Dean");
        map.put(1,"Trkulja");
        assertEquals(1, map.size());
        assertEquals("Trkulja", map.get(1));
    }

    @Test
    public void testAddNullValue(){
        map.put(1,null);
        assertEquals(1,map.size());
        assertNull(map.get(1));
    }

    @Test
    public void testGetWithNullKey(){
        assertNull(mapWithElements.get(null));
    }

    @Test
    public void testGetWitnNonExistingValue(){
        assertNull(mapWithElements.get(12));
    }

    @Test
    public void testGetWithExistingValue(){
        assertEquals("1", mapWithElements.get(1));
        assertEquals("2", mapWithElements.get(2));
        assertEquals("3", mapWithElements.get(3));
    }

    @Test
    public void testSize(){
        assertEquals(4,mapWithElements.size());
        mapWithElements.remove(1);
        assertEquals(3,mapWithElements.size());
        mapWithElements.put(2, "33");
        assertEquals(3, mapWithElements.size());
        mapWithElements.put(123, "33");
        assertEquals(4, mapWithElements.size());
    }

    @Test
    public void testClear(){
        mapWithElements.clear();
        assertEquals(0,mapWithElements.size());
    }

    @Test
    public void testContainsKeyTrue(){
        assertTrue(mapWithElements.containsKey(1));
    }

    @Test
    public void testContainsKeyFalse(){
        assertFalse(mapWithElements.containsKey(123));
    }

    @Test
    public void testContainsKeyNullKey(){
        assertFalse(mapWithElements.containsKey(null));
    }

    @Test
    public void testContainsValueTrue(){
        assertTrue(mapWithElements.containsValue("1"));
    }

    @Test
    public void testContainsValueFalse(){
        assertFalse(mapWithElements.containsValue("123"));
    }
    @Test
    public void testContainsValueNullTrue(){
        mapWithElements.put(1,null);
        assertTrue(mapWithElements.containsValue(null));
    }

    @Test
    public void testContainsValueNullFalse(){
        assertFalse(mapWithElements.containsKey(null));
    }

    @Test
    public void testRemoveNullKey(){
        assertNull(mapWithElements.remove(null));
    }

    @Test
    public void testRemoveNonExistingKey(){
        assertNull(mapWithElements.remove(123));
    }

    @Test
    public void  testRemoveExistingValue(){

        for(int i = 1; i < 5; i++) {
            mapWithElements.remove(i);
            assertEquals(4-i, mapWithElements.size());
            assertFalse(mapWithElements.containsKey(i));
        }

    }

    @Test
    public void testRemove(){
        mapWithStringKeys.remove("Kristina");
        assertFalse(mapWithStringKeys.containsKey("Kristina"));
        mapWithStringKeys.remove("Jasna");
        assertFalse(mapWithStringKeys.containsKey("Jasna"));

    }

    @Test
    public void testToArray(){
        assertEquals(4,mapWithElements.toArray().length);
    }

    @Test
    public void testHasNext(){
        Iterator<SimpleHashtable.TableEntry<Integer,String>> iterator = mapWithElements.iterator();
        assertTrue(iterator.hasNext());
        while(iterator.hasNext())iterator.next();
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testNextWhenThereAreNoMore(){
        Iterator<SimpleHashtable.TableEntry<Integer,String>> iterator2 = map.iterator();
        assertThrows(NoSuchElementException.class, iterator2::next);
    }

    @Test
    public void testModificationWhileIterating(){
        Iterator<SimpleHashtable.TableEntry<Integer,String>> iterator = mapWithElements.iterator();
        iterator.next();
        mapWithElements.put(34,"34");
        assertThrows(ConcurrentModificationException.class, iterator::next);
    }

    @Test
    public void testRemoveFromIterator(){
        Iterator<SimpleHashtable.TableEntry<Integer,String>> iterator = mapWithElements.iterator();
        while (iterator.hasNext()){
            iterator.next();
            iterator.remove();
        }

        assertEquals(0, mapWithElements.size());
    }

    @Test
    public void testTwoRemoveInARowFromIterator(){
        Iterator<SimpleHashtable.TableEntry<Integer,String>> iterator = mapWithElements.iterator();
        iterator.next();
        iterator.remove();
        assertThrows(IllegalStateException.class, iterator::remove);
    }



}
