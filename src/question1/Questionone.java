package question1;

import java.util.Scanner;
import java.util.Stack;

public class Questionone {
    public static boolean isBalanced(String s) {
        Stack<Character> stack = new Stack<Character>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (ch == '(' || ch == '{' || ch == '[') {
                stack.push(ch);

            } else if (ch == ')' || ch == '}' || ch == ']') {
                if (stack.isEmpty()) {
                    return false;
                }

                char top = stack.pop();

                if (ch == ')' && top != '(')
                    return false;
                if (ch == '}' && top != '{')
                    return false;
                if (ch == ']' && top != '[')
                    return false;
            }
        }

        return stack.isEmpty();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter bracket string: ");
        String input = scanner.nextLine();

        if (isBalanced(input)) {
            System.out.println("BALANCED");
        } else {
            System.out.println("NOT BALANCED");
        }

        scanner.close();
    }
}
