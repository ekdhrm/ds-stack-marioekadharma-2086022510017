package question2;

import java.util.Scanner;
import java.util.Stack;

public class Questiontwo {
    public static int evaluateRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            if (token.equals("+") || token.equals("-")
                    || token.equals("*") || token.equals("/")) {

                int b = stack.pop(); // right operand (popped first)
                int a = stack.pop(); // left operand

                int result = 0;

                if (token.equals("+"))
                    result = a + b;
                if (token.equals("-"))
                    result = a - b;
                if (token.equals("*"))
                    result = a * b;
                if (token.equals("/"))
                    result = (int) ((double) a / b); // truncate toward zero

                stack.push(result);
            } else {
                stack.push(Integer.parseInt(token));
            }
        }
        return stack.pop();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of tokens: ");
        int n = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Enter tokens (space-separated): ");
        String line = scanner.nextLine().trim();
        String[] tokens = line.split(" ");

        int result = evaluateRPN(tokens);
        System.out.println("Result: " + result);

        scanner.close();
    }
}
