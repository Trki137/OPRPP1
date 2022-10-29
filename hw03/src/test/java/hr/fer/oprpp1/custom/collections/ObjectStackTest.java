package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ObjectStackTest {
    private ObjectStack<Integer> integerStack;
    @BeforeEach
    public void setUp(){
        integerStack = new ObjectStack<>();
        integerStack.push(1);
        integerStack.push(2);
        integerStack.push(3);
    }

    @Test
    public void testPush(){
        int size = integerStack.size();
        int addedElem = 5;
        integerStack.push(addedElem);
        assertEquals(size + 1, integerStack.size());
        assertEquals(addedElem, integerStack.peek());
    }

    @Test
    public void testPeek(){
        assertEquals(3,integerStack.peek());
    }

    @Test
    public void testPop(){
        int size = integerStack.size();
        assertEquals(3,integerStack.pop());
        assertEquals(size - 1, integerStack.size());
        assertEquals(2, integerStack.peek());
    }

    @Test
    public void testIsEmpty(){
        assertFalse(integerStack.isEmpty());
    }

    @Test
    public void testSize(){
        assertEquals(3, integerStack.size());
    }

    @Test
    public void testClear(){
        integerStack.clear();
        assertEquals(0,integerStack.size());
        assertThrows(EmptyStackException.class, () -> integerStack.pop());
    }
}
