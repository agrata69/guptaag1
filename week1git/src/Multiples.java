public class Multiples {
    public static int countMultiples(int n, int a, int b) {
        int count = 0;

        for (int i = 1; i < n; i++) {
            // Avoid double-counting when a == b
            if (a == b) {
                if (i % a == 0) {
                    count++;
                }
            } else {
                if (i % a == 0 || i % b == 0) {
                    count++;
                }
            }
        }

        return count;
    }

    // Main method for running as a standalone program
    public static void main(String[] args) {
        // Call countMultiples with hardcoded values for demonstration
        int result = countMultiples(1000, 3, 5);
        System.out.println("Number of multiples of 3 or 5 below 1000: " + result);
    }
}