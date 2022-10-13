package hr.fer.oprpp1.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

public class StackDemo {
    public static void main(String[] args) {
        if(args.length != 1) throw new IllegalArgumentException("Argument not valid");
        String[] arr = args[0].split(" ");
        ObjectStack stack = new ObjectStack();
        calculate(arr, stack);
        if (stack.size() != 1) System.out.println("Something went wrong");
        else System.out.println("Expression evaluates to "+ stack.pop() + ".");
    }

    private static void calculate(String[] arr, ObjectStack stack) {
        for(String element : arr) {
            if (!element.matches("[+\\-/*%]")) stack.push(element);
            else{

                int value2 = Integer.parseInt(stack.pop().toString());
                int value1 = Integer.parseInt(stack.pop().toString());

                switch (element){
                    case "+" -> stack.push(value1 + value2);
                    case "-" -> stack.push(value1 - value2);
                    case "*" -> stack.push(value1 * value2);
                    case "/" -> {
                        if(value2 == 0) throw new ArithmeticException("Dividing by zero is not allowed");
                        stack.push(value1/value2);
                    }
                    case "%" -> {
                        if(value2 == 0) throw new ArithmeticException("Dividing by zero is not allowed");
                        stack.push(value1%value2);
                    }
                }
            }
        }
    }
}
