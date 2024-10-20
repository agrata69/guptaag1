package edu.csc207.fall2024;

/**
 * Constructs an AbstractPerformanceCalculator.
 *
 * @param performance the performance object (must not be null)
 * @param play the play object (must not be null)
 */
public abstract class AbstractPerformanceCalculator {
    protected final Performance performance;
    protected final Play play;

    public AbstractPerformanceCalculator(Performance performance, Play play) {
        this.performance = performance;
        this.play = play;
    }
    public abstract int getAmount();

    // Common method for calculating volume credits
    public int getVolumeCredits() {
        int result = Math.max(performance.getAudience() - Constants.BASE_VOLUME_CREDIT_THRESHOLD, 0);
        if ("comedy".equals(play.getType())) {
            result += performance.getAudience() / Constants.COMEDY_EXTRA_VOLUME_FACTOR;
        }
        return result;
    }

    // Factory method to return the correct subclass
    public static AbstractPerformanceCalculator createPerformanceCalculator(Performance performance, Play play) {
        switch (play.getType()) {
            case "tragedy":
                return new TragedyCalculator(performance, play);
            case "comedy":
                return new ComedyCalculator(performance, play);
            default:
                throw new IllegalArgumentException("Unknown play type: " + play.getType());
        }
    }
}
