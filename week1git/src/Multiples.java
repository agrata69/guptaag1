public class Multiples {
    public static int main(int n, int a, int b) {
        int count = 0;

        // Loop through numbers less than n and count multiples of a or b
        for (int i = 1; i < n; i++) {
            if (i % a == 0 || i % b == 0) {
                count++;
            }
        }

        return count;
    }

    // Default main method with no parameters to handle test cases with no arguments
    public static int main() {
        // Default values for n, a, and b
        return main(1000, 3, 5);  // Default case with n = 1000, a = 3, b = 5
    }

    // Standard main method to run the program
    public static void main(String[] args) {
        // Call the default main method for testing
        int result = main();
        System.out.println("Number of multiples of 3 or 5 below 1000: " + result);
    }
}