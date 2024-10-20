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
                    .map(performance -> {
                        Play play = plays.get(performance.getPlayID());
                        AbstractPerformanceCalculator calculator = AbstractPerformanceCalculator.createPerformanceCalculator(performance, play);
                        int amount = calculator.calculateAmount();
                        int volumeCredits = calculator.calculateVolumeCredits();
                        return new PerformanceData(performance, play, amount, volumeCredits);
                    })
                    .collect(Collectors.toList());
        }

        public String getCustomer() {
            return customer;
        }

        public List<PerformanceData> getPerformances() {
            return performances;
        }

        public int totalAmount() {
            return performances.stream()
                    .mapToInt(PerformanceData::getAmount)
                    .sum();
        }

        public int volumeCredits() {
            return performances.stream()
                    .mapToInt(PerformanceData::getVolumeCredits)
                    .sum();
        }
    }

        /**
         * Private helper method to create a PerformanceData object.
         *
         * @param performance the performance object
         * @param play the play object
         * @return the created PerformanceData object
         */
        private PerformanceData createPerformanceData(Performance performance, Play play) {
            // Using the factory method to create an AbstractPerformanceCalculator
            AbstractPerformanceCalculator calculator = AbstractPerformanceCalculator.createPerformanceCalculator(performance, play);

            // Create a PerformanceData object (for now, PerformanceData will still handle the data)
            return new PerformanceData(performance, play);
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
        private final int amount;
        private final int volumeCredits;

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

        public int getAmount() {
            return amount;
        }

        public int getVolumeCredits() {
            return volumeCredits;
        }
    }
    /**
     * This class generates an HTML formatted statement for a given invoice of performances.
     */

    public class HTMLStatementPrinter extends StatementPrinter {

        private StatementData statementData;

        public HTMLStatementPrinter(Invoice invoice, Map<String, Play> plays) {
            super(invoice, plays);
        }

        @Override
        public String statement() {
            final StringBuilder result = new StringBuilder(String.format("<h1>Statement for %s</h1>%n",
                    statementData.getCustomer()));
            result.append("<table>").append(System.lineSeparator());
            result.append(String.format(" <caption>Statement for %s</caption>%n", statementData.getCustomer()));
            result.append(" <tr><th>play</th><th>seats</th><th>cost</th></tr>").append(System.lineSeparator());

            for (PerformanceData performanceData : statementData.getPerformances()) {
                result.append(String.format(" <tr><td>%s</td><td>%d</td><td>%s</td></tr>%n",
                        performanceData.getPlayName(),
                        performanceData.getAudience(),
                        usd(performanceData.getAmount())));
            }

            result.append("</table>").append(System.lineSeparator());
            result.append(String.format("<p>Amount owed is <em>%s</em></p>%n", usd(statementData.totalAmount())));
            result.append(String.format("<p>You earned <em>%d</em> credits</p>%n", statementData.volumeCredits()));

            return result.toString();
        }
    }
}
