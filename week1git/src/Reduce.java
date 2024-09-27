public class Reduce {
    public static int reduceSteps(int n, int a, int b) {
        int steps = 0;

        // Loop until n is reduced to 0
        while (n > 0) {
            if (n % a == 0) {
                n /= a;
            } else if (n % b == 0) {
                n -= b;
            }
            steps++;
        }

        return steps;
    }

    // Overloaded main method with one integer parameter
    public static int main(int n) {
        // Call reduceSteps with default values for a and b (e.g., a = 2, b = 3)
        return reduceSteps(n, 2, 3);
    }

    // Default main method with no parameters to handle test cases with no arguments
    public static int main() {
        // Default case with n = 100, a = 2, b = 3
        return main(100);
    }

    // Standard main method to run the program
    public static void main(String[] args) {
        // Call the default main method for testing
        int result = main();
        System.out.println("Number of steps to reduce 100 using 2 and 3: " + result);
    }
}