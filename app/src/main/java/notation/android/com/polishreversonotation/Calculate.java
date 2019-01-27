package notation.android.com.polishreversonotation;

import android.util.Log;

import java.util.LinkedList;
import java.util.Stack;

/**
 * A class which translates the expression into reverse
 * Polish notation and calculates it. It also contains auxiliary
 * methods for calculating
 */
class Calculate {

    /**
     * Fields constant type of Strings with tags for logging code
     */
    private static final String TAG_TRANSFER = "TransferLog";
    private static final String TAG_CALCULATE = "CalculateLog";

    /**
     * Fields collection serves for contains stack of operator and number
     */
    private Stack<String> stackOperator = new Stack<>();
    private Stack<Integer> stackNumber = new Stack<>();

    /**
     * Calculating Polish expression
     *
     * @param exp gets this argument as LinkedList contains Polish expression
     * @return result of calculating expression of String type
     */
    String calculateFromPolish(LinkedList<String> exp) {
        Log.i(TAG_CALCULATE, "Calculating postfix notation");

        for (String currentSymbol : exp) {
            Log.i(TAG_CALCULATE, "Reading: " + currentSymbol);
            if (isNumber(currentSymbol)) {
                Log.i(TAG_CALCULATE, "Current symbol " + currentSymbol
                        + " is number, so putting it to stack");
                stackNumber.push(convertStringToInt(currentSymbol));
            } else if (isOperator(currentSymbol)) {
                stackNumber.push(operations(stackNumber.pop(), currentSymbol, stackNumber.pop()));
            }

            Log.i(TAG_CALCULATE, "Stack: " + stackNumber +
                    "\n--------------------------------------------");
        }
        return String.valueOf(stackNumber);
    }

    /**
     * Converts expression to Polish reverse notation via collection type of LinkedList
     *
     * @param exp contains the standard expression
     * @return Polish reverse notation into LinkedList
     */

    LinkedList<String> transferIntoPolish(LinkedList<String> exp) {
        LinkedList<String> list = new LinkedList<>();

        Log.i(TAG_TRANSFER, "Converting expression into reverse Polish notation\n");

        for (String current : exp) {
            Log.i(TAG_TRANSFER, "Reading: " + current);

            if (current.equals("(")) {
                Log.i(TAG_TRANSFER, "Putting: " + current + " in stack");
                stackOperator.push(current);
            } else if (current.equals(")")) {
                while (!(stackOperator.peek()).equals("(")) {
                    Log.i(TAG_TRANSFER, "Pulling out of stack " + stackOperator.peek()
                            + " until the top element becomes (");

                    list.add(stackOperator.pop());
                }
                Log.i(TAG_TRANSFER, "Now removing from stack (");
                stackOperator.pop();
            } else if (isOperator(current)) {
                while (!stackOperator.isEmpty() && (getPriority(current) <= getPriority(stackOperator.peek()))) {
                    Log.i(TAG_TRANSFER, "Priority " + current
                            + " is less or equal operator," +
                            " pulling  out the top operator from stack to line");
                    list.add(stackOperator.pop());
                }
                stackOperator.push(current);
            } else {
                Log.i(TAG_TRANSFER, "Putting: " + current + " to line");
                list.add(current);
            }

            Log.i(TAG_TRANSFER, "Line: " + list);
            Log.i(TAG_TRANSFER, "Stack: " + stackOperator
                    + "\n--------------------------------------------");
        }

        while (!stackOperator.isEmpty())
            list.add(stackOperator.pop());

        return list;
    }

    /**
     * Method checks is an argument number
     *
     * @param s String which will be checked
     * @return checked result of incoming String
     */
    private boolean isNumber(String s) {
        return s.matches("[0-9]+");
    }

    /**
     * Method checks is an argument operator
     *
     * @param s String which will be checked
     * @return checked result of incoming String
     */
    private boolean isOperator(String s) {
        return s.equals("-") || s.equals("+") || s.equals("/") || s.equals("*");
    }

    /**
     * Method allow to get priority of operators
     *
     * @param s String contains incoming operator
     * @return the integer priority incoming operator
     */
    private int getPriority(String s) {
        switch (s) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            default:
                return -1;
        }
    }

    /**
     * Method helps convert String into operation so calculate two incoming number
     *
     * @param a        int contains a first number to calculate
     * @param operator String containing operator for calculate two input int
     * @param b        int contains a second number to calculate
     * @return result of calculating incoming int via operator
     */
    private int operations(int a, String operator, int b) {
        Log.i(TAG_CALCULATE, "Current symbol " + " is operations, so do it"
                + b + operator + a + " and putting result to stack");
        switch (operator) {
            case "+":
                return b + a;
            case "-":
                return b - a;
            case "*":
                return b * a;
            case "/":
                return b / a;
            default:
                return 0;
        }
    }

    /**
     * Simple converter object type String to primitive type int
     *
     * @param s String containing the int representation to be parsed
     * @return converted number of int type
     */
    private int convertStringToInt(String s) {
        return Integer.parseInt(s);
    }
}