package question4;

import java.util.Scanner;
import java.util.Stack;

public class Questionfour {

    /*
     * Stacking Formation Injection:
     *   Sort the array using a stack so that the smallest value sits
     *   at the bottom and the largest sits at the top.
     *
     *   For each new number:
     *     1. Pop everything larger than it into a temporary stack.
     *     2. Push the new number.
     *     3. Push everything back from the temp stack.
     *
     *   After processing all numbers, the sorted stack (smallest at
     *   bottom, largest at top) is collected into an array by popping
     *   and filling right-to-left.
     */

    public static int[] stackSort(int[] nums) {
        Stack<Integer> sorted = new Stack<Integer>();

        for (int i = 0; i < nums.length; i++) {
            int current = nums[i];

            // Temporarily move elements that are larger than current
            Stack<Integer> temp = new Stack<Integer>();
            while (!sorted.isEmpty() && sorted.peek() > current) {
                temp.push(sorted.pop());
            }

            sorted.push(current);

            // Put the displaced elements back
            while (!temp.isEmpty()) {
                sorted.push(temp.pop());
            }
        }

        // Collect result: pop gives largest first, fill array right-to-left
        int[] result = new int[nums.length];
        for (int i = nums.length - 1; i >= 0; i--) {
            result[i] = sorted.pop();
        }

        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter numbers (space-separated): ");
        String[] parts = scanner.nextLine().trim().split(" ");

        int[] nums = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            nums[i] = Integer.parseInt(parts[i]);
        }

        int[] result = stackSort(nums);

        for (int i = 0; i < result.length; i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(result[i]);
        }
        System.out.println();

        scanner.close();
    }
}