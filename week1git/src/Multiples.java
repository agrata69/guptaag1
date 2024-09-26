public class Multiples {
    public static int countMultiples(int n, int a, int b) {
        int count = 0;

        for (int i = 1; i < n; i++) {
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

    public static void main(String[] args) {
        int result = countMultiples(1000, 3, 5);
        System.out.println("Number of multiples of 3 or 5 below 1000: " + result);
    }
}