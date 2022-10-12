package hr.fer.oprpp1.demo;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.ObjectStack;

import java.util.Arrays;
import java.util.List;

public class StackDemo {
    public static void main(String[] args) {
        String[] arr = args[0].split(" ");
        List<String> operands = Arrays.asList(new String[]{"+","-", "*", "/", "(", ")"});
        ObjectStack operandStack = new ObjectStack();
        ObjectStack valueStack = new ObjectStack();
        for(String element : arr) {
            if (operands.contains(element)) operandStack.push(element);
            else valueStack.push(element);
        }
    }
}
