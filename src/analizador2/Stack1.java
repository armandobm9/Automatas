package analizador2;

import java.util.Stack;


public class Stack1 {

    Character getTopOfOperator(Stack<Character> stack){
        if (stack.isEmpty()){
            return 'e';
        }

        Character top=stack.peek();
        return top;
    }

}