import java.util.Iterator;
import java.util.LinkedList;

public class LongStack {

    private LinkedList<Long> lifo;

    public static void main (String[] argum) {

        System.out.println(LongStack.interpret("-234 +"));

    }

    LongStack() {
        this.lifo = new LinkedList<>();
    }

    LongStack(LinkedList<Long> lifo) {
        this.lifo = lifo;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new LongStack((LinkedList<Long>) this.lifo.clone());
    }

    public boolean stEmpty() {
        return this.lifo.isEmpty();
    }

    public void push (long a) {
        this.lifo.push(a);
    }

    public long pop() {
        if(stEmpty()) {
            throw new IndexOutOfBoundsException("pop");
        }
        return this.lifo.pop();
    }

    public void op (String s) {
        if(lifo.size() < 2){
            throw new RuntimeException("Too few elements, requires at least 2 integers and 1 operations    ");
        }
        long op1 = this.pop();
        long op2 = this.pop();
        switch (s) {
            case "-":
                push(op2 - op1);
                break;
            case "+":
                push(op2 + op1);
                break;
            case "*":
                push(op2 * op1);
                break;
            case "/":
                push(op2 / op1);
                break;
            default:
                throw new RuntimeException("Illegal operation '" + s +"' valid operations are +,- , * and /" );
        }



    }


    public long tos() {
        if (stEmpty()) {
            throw new IndexOutOfBoundsException("tos got empty stack");
        }
        return this.lifo.getFirst();
    }

    @Override
    public boolean equals (Object o) {
        return this.lifo.equals(((LongStack)o).lifo);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        Iterator<Long> i = this.lifo.descendingIterator();
        while (i.hasNext()) {
            s.append(i.next());
            s.append(" ");
        }
        return s.toString();
    }

    public static long interpret (String pol) {
        if (pol == null || pol.length() == 0 || pol.matches("\\s+")){
            throw new RuntimeException("Expression can't be empty");
        }
        LongStack stack = new LongStack();
        // \\s+ - matches sequence of one or more whitespace characters
        String[] elements = pol.trim().split("\\s+");
        int i = 0, o= 0;
        for (String element : elements){
            try {
                stack.push(Integer.valueOf(element));
                i++;
            }catch (NumberFormatException e ) {
                if (!element.equals("+") && !element.equals("-") && !element.equals("*") && !element.equals("/"))
                    throw new RuntimeException("Expression:[" + pol + "] contains illegal symbol '" + element + "'.");
                else if (stack.stEmpty())
                    throw new RuntimeException("Expression [" + pol +"]. Operation can't be the first element of expression.");
                else if (elements.length <= 2)
                    throw  new RuntimeException(" Expression ["+ pol +  "]. Too few elements, requires at least 2 integers and 1 operations.");
                stack.op(element);
                o++;
            }
        }
        if (i - 1 != o)
            throw new RuntimeException("Expression: ["+ pol + "] is out of balance (integers: " + i + ", operations: " + o + ").");
        return stack.pop();
    }


}

