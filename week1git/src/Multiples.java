public class Multiples {
    public static int main(int n, int a, int b) {
        int count = 0;

        // Loop through all numbers less than n and count multiples of a or b
        for (int i = 1; i < n; i++) {
            if (i % a == 0 || i % b == 0) {
                count++;
            }
        }

        // Return the count
        return count;
    }

    // Standard main method for running the program
    public static void main(String[] args) {
        // Call the overloaded main method for testing
        int result = main(1000, 3, 5);
        System.out.println("Number of multiples of 3 or 5 below 1000: " + result);
    }
}