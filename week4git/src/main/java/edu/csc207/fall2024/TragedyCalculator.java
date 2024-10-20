package edu.csc207.fall2024;

public class TragedyCalculator extends AbstractPerformanceCalculator {

    public TragedyCalculator(Performance performance, Play play) {
        super(performance, play);
    }

    @Override
    public int getAmount() {
        int result = StatementPrinter.TRAGEDY_BASE_AMOUNT;
        if (performance.getAudience() > StatementPrinter.TRAGEDY_AUDIENCE_THRESHOLD) {
            result += StatementPrinter.TRAGEDY_EXTRA_AUDIENCE_AMOUNT * (performance.getAudience() - StatementPrinter.TRAGEDY_AUDIENCE_THRESHOLD);
        }
        return result;
    }

    @Override
    public int getVolumeCredits() {
        return 0;
    }
}
