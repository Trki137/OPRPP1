package hr.fer.oprpp1.custom.collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
public class DictionaryTest {
    private Dictionary<Integer, String> map;
    private Dictionary<String,String> mapString;
    @BeforeEach
    public void setUp(){
        map = new Dictionary<>();
        mapString = new Dictionary<>();
        map.put(1,"A");
        map.put(2,"B");
        map.put(3,"C");
        map.put(4,"D");

        mapString.put("Dean", "Trkulja");
        mapString.put("Java", "OOP");
        mapString.put("Hello", "World");
    }
    @Test
    public void testSize(){
        int size = map.size();
        map.put(5, "E");
        assertEquals(size + 1, map.size());
        map.put(5,"J");
        assertEquals(size + 1, map.size());
        map.remove(1);
        assertEquals(size, map.size());
    }

    @Test
    public void testAddingToDictionary(){
        int size = map.size();
        map.put(5,"E");
        assertEquals(size + 1, map.size());
        assertEquals("E", map.get(5));
    }

    @Test
    public void testAddingToDictionaryExistingKey(){
        int size = map.size();
        map.put(1,"J");
        map.put(3,"K");
        assertEquals(size, map.size());
        assertEquals("J", map.get(1));
        assertEquals("K",map.get(3) );
    }

    @Test
    public void testClear(){
        map.clear();
        assertEquals(0,map.size());
        assertTrue(map.isEmpty());
    }

    @Test
    public void testIsEmpty(){
        assertFalse(mapString.isEmpty());
        mapString.clear();
        assertTrue(mapString.isEmpty());
        mapString.put("A", "B");
        assertFalse(map.isEmpty());
    }

    @Test
    public void testGetElement(){
        assertEquals(mapString.get("Dean"), "Trkulja");
        assertNull(mapString.get("Deki"));
    }

    @Test
    public void testRemove(){
        int size = mapString.size();
        mapString.remove("Dean");
        assertEquals(size - 1, mapString.size());
        assertNull(mapString.get("Dean"));
        assertEquals("OOP", mapString.get("Java"));
        mapString.remove("Hello");
        assertNull(mapString.get("Hello"));
    }

    @Test
    public void testNullPair(){
        assertThrows(NullPointerException.class, () -> mapString.put(null, "Dean"));
    }

    @Test
    public void removeNull(){
        assertThrows(NullPointerException.class, () -> mapString.remove(null));
    }

    @Test
    public void getNull(){
        assertThrows(NullPointerException.class, () -> mapString.get(null));
    }



}
