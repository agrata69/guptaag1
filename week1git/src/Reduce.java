public class Reduce {
    public static int reduceSteps(int n, int a, int b) {
        int steps = 0;

        // Loop until n is reduced to 0
        while (n > 0) {
            if (n % a == 0) {
                n /= a;
            } else if (n % b == 0) {
                n -= b;
            } else {
                n -= 1;
            }
            steps++;
        }

        return steps;
    }

    // Default main method with no parameters to handle test cases with no arguments
    public static int main() {
        // Default values for n, a, and b
        return reduceSteps(100, 2, 3);  // Default case with n = 100, a = 2, b = 3
    }

    // Standard main method to run the program
    public static void main(String[] args) {
        // Call the default main method for testing
        int result = main();
        System.out.println("Number of steps to reduce 100 using 2 and 3: " + result);
    }
}