package question5;

import java.util.Scanner;
import java.util.Stack;

public class Questionfive {

    /*
     * Pattern observed from test cases:
     *   n=50  ->  1 3 6 12 25 50
     *   n=75  ->  1 2 4  9 18 37 75
     *
     * Each value is roughly half the next: 50->25->12->6->3->1
     * Using integer division (n/2) repeatedly until we reach 0.
     *
     * A stack naturally reverses the sequence:
     *   Push:  50, 25, 12, 6, 3, 1
     *   Pop:    1,  3,  6, 12, 25, 50  <- ascending order
     */

    public static int[] calculateMomentum(int n) {
        Stack<Integer> stack = new Stack<Integer>();

        int current = n;
        while (current >= 1) {
            stack.push(current);
            current = current / 2;
        }

        int[] result = new int[stack.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = stack.pop();
        }

        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter velocity n: ");
        int n = Integer.parseInt(scanner.nextLine().trim());

        int[] result = calculateMomentum(n);

        for (int i = 0; i < result.length; i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(result[i]);
        }
        System.out.println();

        scanner.close();
    }
}