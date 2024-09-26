public class Reduce {
    public static int reduceSteps(int n, int a, int b) {
        int steps = 0;

        while (n > 0) {
            if (n % a == 0) {
                n /= a;
            }
            else if (n % b == 0) {
                n -= b;
            }
            else {
                n -= 1;
            }
            steps++;
        }

        return steps;
    }

    public static void main(String[] args) {
        int result = reduceSteps(100, 2, 1);
        System.out.println("Steps to reduce 100 to 0: " + result);
    }
}
