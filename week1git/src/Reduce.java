public class Reduce {
    public static int reduceSteps(int n) {
        int steps = 0;

        // Repeat the process until n becomes 0
        while (n > 0) {
            if (n % 2 == 0) {
                n /= 2;  // If n is even, divide by 2
            } else {
                n -= 1;  // If n is odd, subtract 1
            }
            steps++;  // Increment the step counter
        }

        return steps;  // Return the number of steps
    }

    // Overloaded main method that accepts an integer n and returns the number of steps
    public static int main(int n) {
        // Call reduceSteps with the given n
        return reduceSteps(n);
    }

    // Standard main method to run the program
    public static void main(String[] args) {
        // Call the overloaded main method with a default value of 100
        int result = main(100);
        System.out.println("Number of steps to reduce 100 to 0: " + result);
    }
}