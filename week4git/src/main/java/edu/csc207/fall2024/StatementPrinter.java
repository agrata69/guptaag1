package edu.csc207.fall2024;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class generates a statement for a given invoice of performances.
 */
public class StatementPrinter {

    private static final int TRAGEDY_BASE_AMOUNT = 40000;
    private static final int TRAGEDY_EXTRA_AUDIENCE_AMOUNT = 1000;
    private static final int TRAGEDY_AUDIENCE_THRESHOLD = 30;
    private static final int PERCENT_FACTOR = 100;
    private final StatementData statementData;

    public StatementPrinter(Invoice invoice, Map<String, Play> plays) {
        if (invoice == null || plays == null) {
            throw new IllegalArgumentException("Invoice and plays map cannot be null");
        }
        this.statementData = new StatementData(invoice, plays);
    }

    /**
     * Returns a formatted statement of the invoice associated with this printer.
     * @return the formatted statement
     * @throws RuntimeException if one of the play types is not known
     */
    public String statement() {
        final StringBuilder result = new StringBuilder("Statement for " + statementData.getCustomer() + "\n");

        final int totalAmount = statementData.totalAmount();
        final int volumeCredits = statementData.volumeCredits();

        return renderPlainText(result, totalAmount, volumeCredits);
    }

    private String renderPlainText(StringBuilder result, int totalAmount, int volumeCredits) {
        for (PerformanceData performanceData : statementData.getPerformances()) {
            result.append(String.format("  %s: %s (%d seats)%n",
                    performanceData.getPlayName(),
                    usd(performanceData.getAmount()),
                    performanceData.getAudience()));
        }
        result.append(String.format("Amount owed is %s%n", usd(totalAmount)));
        result.append(String.format("You earned %d credits", volumeCredits));
        return result.toString().trim();
    }

    private static String usd(int totalAmount) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(totalAmount / PERCENT_FACTOR);
    }

    private static int volumeCreditsFor(PerformanceData performanceData, int result) {
        result += result + Math.max(performanceData.getAudience() - Constants.BASE_VOLUME_CREDIT_THRESHOLD, 0);
        if ("comedy".equals(performanceData.getType())) {
            result += performanceData.getAudience() / Constants.COMEDY_EXTRA_VOLUME_FACTOR;
        }
        return result;
    }

    public class StatementData {
        private final String customer;
        private final List<PerformanceData> performances;

        public StatementData(Invoice invoice, Map<String, Play> plays) {
            this.customer = invoice.getCustomer();
            this.performances = invoice.getPerformances().stream()
                    .map(performance -> new PerformanceData(performance, plays.get(performance.getPlayID())))
                    .collect(Collectors.toList());
        }

        public String getCustomer() {
            return customer;
        }

        public List<PerformanceData> getPerformances() {
            return performances;
        }

        /**
         * Calculates the total amount for all performances.
         *
         * @return the total amount in cents for all performances.
         */
        public int totalAmount() {
            return performances.stream()
                    .mapToInt(PerformanceData::getAmount)
                    .sum();
        }

        /**
         * Calculates the total volume credits for all performances.
         *
         * @return the total volume credits.
         */
        public int volumeCredits() {
            int volumeCredits = 0;
            for (PerformanceData performanceData : performances) {
                volumeCredits = StatementPrinter.volumeCreditsFor(performanceData, volumeCredits);
            }
            return volumeCredits;
        }
    }

    public class PerformanceData {
        private final Performance performance;
        private final Play play;

        public PerformanceData(Performance performance, Play play) {
            this.performance = performance;
            this.play = play;
        }

        public int getAudience() {
            return performance.getAudience();
        }

        public String getType() {
            return play.getType();
        }

        public String getPlayName() {
            return play.getName();
        }

        private int getAmount() {
            int result;
            switch (play.getType()) {
                case "tragedy":
                    result = StatementPrinter.TRAGEDY_BASE_AMOUNT;
                    if (performance.getAudience() > StatementPrinter.TRAGEDY_AUDIENCE_THRESHOLD) {
                        result += StatementPrinter.TRAGEDY_EXTRA_AUDIENCE_AMOUNT * (performance.getAudience()
                                - StatementPrinter.TRAGEDY_AUDIENCE_THRESHOLD);
                    }
                    break;
                case "comedy":
                    result = Constants.COMEDY_BASE_AMOUNT;
                    if (performance.getAudience() > Constants.COMEDY_AUDIENCE_THRESHOLD) {
                        result += Constants.COMEDY_OVER_BASE_CAPACITY_AMOUNT
                                + (Constants.COMEDY_OVER_BASE_CAPACITY_PER_PERSON * (performance.getAudience()
                                - Constants.COMEDY_AUDIENCE_THRESHOLD));
                    }
                    result += Constants.COMEDY_AMOUNT_PER_AUDIENCE * performance.getAudience();
                    break;
                default:
                    throw new RuntimeException("unknown type: " + play.getType());
            }
            return result;
        }
    }
}
