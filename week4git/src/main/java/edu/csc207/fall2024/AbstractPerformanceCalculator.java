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

    // Existing abstract methods
    public abstract int getAmount();

    public abstract int getVolumeCredits();

    // Moved methods (generalized)

    public int calculateAmount() {
        int result;
        switch (play.getType()) {
            case "tragedy":
                result = StatementPrinter.TRAGEDY_BASE_AMOUNT;
                if (performance.getAudience() > StatementPrinter.TRAGEDY_AUDIENCE_THRESHOLD) {
                    result += StatementPrinter.TRAGEDY_EXTRA_AUDIENCE_AMOUNT * (performance.getAudience() - StatementPrinter.TRAGEDY_AUDIENCE_THRESHOLD);
                }
                break;
            case "comedy":
                result = Constants.COMEDY_BASE_AMOUNT;
                if (performance.getAudience() > Constants.COMEDY_AUDIENCE_THRESHOLD) {
                    result += Constants.COMEDY_OVER_BASE_CAPACITY_AMOUNT + (Constants.COMEDY_OVER_BASE_CAPACITY_PER_PERSON * (performance.getAudience() - Constants.COMEDY_AUDIENCE_THRESHOLD));
                }
                result += Constants.COMEDY_AMOUNT_PER_AUDIENCE * performance.getAudience();
                break;
            default:
                throw new RuntimeException("Unknown play type: " + play.getType());
        }
        return result;
    }

    public int calculateVolumeCredits() {
        int result = Math.max(performance.getAudience() - Constants.BASE_VOLUME_CREDIT_THRESHOLD, 0);
        if ("comedy".equals(play.getType())) {
            result += performance.getAudience() / Constants.COMEDY_EXTRA_VOLUME_FACTOR;
        }
        return result;
    }

    // Factory method to create appropriate calculator
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