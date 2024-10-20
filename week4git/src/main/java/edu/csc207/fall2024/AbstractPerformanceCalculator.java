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

    // Abstract method for calculating amount
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
    public AbstractPerformanceCalculator createPerformanceCalculator(Performance performance, Play play) {
        switch (play.getType()) {
            case "tragedy":
                return new TragedyCalculator(performance, play);
            case "comedy":
                return new ComedyCalculator(performance, play);
            case "history":
                return new HistoryCalculator(performance, play);
            case "pastoral":
                return new PastoralCalculator(performance, play);
            default:
                throw new IllegalArgumentException("Unknown play type: " + play.getType());
        }
    }

    public class HistoryCalculator extends AbstractPerformanceCalculator {

        public HistoryCalculator(Performance performance, Play play) {
            super(performance, play);
        }

        @Override
        public int getAmount() {
            int result = HISTORY_BASE_AMOUNT;
            if (performance.getAudience() > HISTORY_AUDIENCE_THRESHOLD) {
                result += HISTORY_OVER_BASE_CAPACITY_PER_PERSON * (performance.getAudience() - HISTORY_AUDIENCE_THRESHOLD);
            }
            return result;
        }

        @Override
        public int getVolumeCredits() {
            return Math.max(performance.getAudience() - HISTORY_VOLUME_CREDIT_THRESHOLD, 0);
        }
    }

    public class PastoralCalculator extends AbstractPerformanceCalculator {

        public PastoralCalculator(Performance performance, Play play) {
            super(performance, play);
        }

        @Override
        public int getAmount() {
            int result = TRAGEDY_BASE_AMOUNT;
            if (performance.getAudience() > PASTORAL_AUDIENCE_THRESHOLD) {
                result += PASTORAL_OVER_BASE_CAPACITY_PER_PERSON * (performance.getAudience() - PASTORAL_AUDIENCE_THRESHOLD);
            }
            return result;
        }

        @Override
        public int getVolumeCredits() {
            return Math.max(performance.getAudience() - PASTORAL_VOLUME_CREDIT_THRESHOLD, 0) + (performance.getAudience() / 2);
        }
    }
}

