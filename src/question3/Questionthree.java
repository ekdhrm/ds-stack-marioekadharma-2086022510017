package question3;

import java.util.Scanner;
import java.util.Stack;

public class Questionthree {

    /*
     * Machine rules:
     *   '1' -> push 1 onto the stack
     *   'd' -> duplicate the top element
     *   '+' -> pop top two (a, b), decrement all remaining elements by 1,
     *          remove any that become 0, then push (a + b)
     *
     * Goal: generate the instruction string that produces the given
     *       target stack state (index 0 = bottom, last index = top).
     *
     * Key insight — adjusted values:
     *   Every '+' op decrements all elements currently below it on the stack.
     *   Building position i first means the '+' ops from positions above it
     *   will later decrement it. To end up with target[i] we must build:
     *       adjusted[i] = target[i] + (count of all '+' ops fired above position i)
     *
     *   We compute right-to-left and push each adjusted value onto a Stack.
     *   Popping that Stack delivers them left-to-right for building.
     *
     * build(v) — binary decomposition:
     *   v == 1        -> "1"
     *   v is even     -> build(v/2) + "d+"    (double via dup-then-merge)
     *   v is odd > 1  -> build(v-1) + "1+"    (increment by 1)
     */

    // Count how many '+' operations build(v) will emit
    public static int countPlus(int v) {
        if (v == 1) return 0;
        if (v % 2 == 0) return countPlus(v / 2) + 1;
        return countPlus(v - 1) + 1;
    }

    // Produce the instruction string that builds value v on top of the stack
    public static String build(int v) {
        if (v == 1) return "1";
        if (v % 2 == 0) return build(v / 2) + "d+";
        return build(v - 1) + "1+";
    }

    // Generate the full instruction string for a target stack.
    // target[0] = bottom element, target[n-1] = top element.
    // Uses a Stack<Integer> to store adjusted values right-to-left,
    // so popping delivers them in left-to-right (build) order.
    public static String generate(int[] target) {
        Stack<Integer> adjustedStack = new Stack<Integer>();

        // Right-to-left pass: compute and push each adjusted value
        int futurePlus = 0;
        for (int i = target.length - 1; i >= 0; i--) {
            int adj = target[i] + futurePlus;
            adjustedStack.push(adj);
            futurePlus += countPlus(adj);
        }

        // Pop left-to-right: build instruction string for each position
        String instructions = "";
        while (!adjustedStack.isEmpty()) {
            instructions += build(adjustedStack.pop());
        }

        return instructions;
    }

    // Simulate the machine on an instruction string using a pure Stack.
    // For '+': pop a and b, move remaining elements to a temp Stack
    //          (decrementing each by 1, discarding zeros), push (a+b).
    // Returns the machine Stack after all instructions (top = most recent push).
    public static Stack<Integer> simulate(String instructions) {
        Stack<Integer> machine = new Stack<Integer>();

        for (int i = 0; i < instructions.length(); i++) {
            char op = instructions.charAt(i);

            if (op == '1') {
                machine.push(1);

            } else if (op == 'd') {
                machine.push(machine.peek()); // duplicate top

            } else if (op == '+') {
                int a = machine.pop(); // top  (right operand)
                int b = machine.pop(); // second (left operand)

                // Move remaining elements to temp, decrement, skip zeros
                Stack<Integer> temp = new Stack<Integer>();
                while (!machine.isEmpty()) {
                    int val = machine.pop() - 1;
                    if (val > 0) {
                        temp.push(val);
                    }
                }
                // Restore from temp back to machine (pop temp = original bottom-up order)
                while (!temp.isEmpty()) {
                    machine.push(temp.pop());
                }

                machine.push(a + b);
            }
        }

        return machine;
    }

    // Print the stack bottom-to-top without an array.
    // Uses a temp Stack to reverse: pop machine -> push temp -> pop temp = bottom-first.
    public static void printBottomToTop(Stack<Integer> machine) {
        Stack<Integer> temp = new Stack<Integer>();
        while (!machine.isEmpty()) {
            temp.push(machine.pop());
        }
        System.out.print("[");
        while (!temp.isEmpty()) {
            System.out.print(temp.pop());
            if (!temp.isEmpty()) System.out.print(", ");
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of elements: ");
        int n = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Enter target stack (bottom to top, space-separated): ");
        String[] parts = scanner.nextLine().trim().split(" "); // String[] is necessary for split()

        int[] target = new int[n]; // int[] is necessary to hold parsed scanner input
        for (int i = 0; i < n; i++) {
            target[i] = Integer.parseInt(parts[i]);
        }

        String instructions = generate(target);
        System.out.println(instructions);

        scanner.close();
    }
}