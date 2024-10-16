package edu.csc207.fall2024;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

/**
 * This class generates a statement for a given invoice of performances.
 */
public class StatementPrinter {

    private static final int TRAGEDY_BASE_AMOUNT = 40000;
    private static final int TRAGEDY_EXTRA_AUDIENCE_AMOUNT = 1000;
    private static final int TRAGEDY_AUDIENCE_THRESHOLD = 30;
    private static final int PERCENT_FACTOR = 100;

    private final Invoice invoice;
    private static Map<String, Play> plays = Map.of();

    public StatementPrinter(Invoice invoice, Map<String, Play> plays) {
        if (invoice == null || plays == null) {
            throw new IllegalArgumentException("Invoice and plays map cannot be null");
        }
        this.invoice = invoice;
        this.plays = plays;
    }

    /**
     * Returns a formatted statement of the invoice associated with this printer.
     * @return the formatted statement
     * @throws RuntimeException if one of the play types is not known
     */
    public String statement() {
        int totalAmount = 0;
        final Performance p = null;
        int volumeCredits = Math.max(p.getAudience() - Constants.BASE_VOLUME_CREDIT_THRESHOLD, 0);

        String result = "Statement for " + invoice.getCustomer() + "\n";

        for (Performance performance : invoice.getPerformances()) {
            final int thisAmount = getAmount(performance, getPlay(performance));

            // print line for this order
            result += String.format("  %s: %s (%s seats)%n", getPlay(performance).getName(),
                    NumberFormat.getCurrencyInstance(Locale.US).format(thisAmount / PERCENT_FACTOR), performance.getAudience());
            totalAmount += getAmount(performance, getPlay(performance));
        }
        result = getString(result, totalAmount, volumeCredits);
        return result;

    }

    private String getString(String result, int totalAmount, int volumeCredits) {
        for (Performance performance : invoice.getPerformances()) {
            final int thisAmount = getAmount(performance, getPlay(performance));

            result += String.format("Amount owed is %s%n", usd(totalAmount));
        result += String.format("You earned %s credits\n", getVolumeCredits(volumeCredits));
        }
        return result;
    }

    private int getVolumeCredits(int volumeCredits) {
        for (Performance performance : invoice.getPerformances()) {

            final int thisAmount = getAmount(performance, getPlay(performance));
            // add volume credits
            volumeCredits = volumeCreditsFor(performance, volumeCredits);
        }
        return volumeCredits;
    }

    private static String usd(int totalAmount) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(totalAmount / PERCENT_FACTOR);
    }

    private static int volumeCreditsFor(Performance performance, int results) {
        results += Math.max(performance.getAudience() - Constants.BASE_VOLUME_CREDIT_THRESHOLD, 0);
        // add extra credit for every five comedy attendees
        if ("comedy".equals(getPlay(performance).getType())) {
            results += performance.getAudience() / Constants.COMEDY_EXTRA_VOLUME_FACTOR;
        }
        return results;
    }

    private static Play getPlay(Performance performance) {
        return plays.get(performance.getPlayID());
    }

    private static int getAmount(Performance performance, Play play) {

        return amountFor(performance);
    }

    private static int amountFor(Performance performance) {
        int result;
        switch (getPlay(performance).getType()) {
            case "tragedy":
                result = TRAGEDY_BASE_AMOUNT;
                if (performance.getAudience() > Constants.TRAGEDY_AUDIENCE_THRESHOLD) {
                    result += TRAGEDY_EXTRA_AUDIENCE_AMOUNT * (performance.getAudience()
                            - TRAGEDY_AUDIENCE_THRESHOLD);
                }
                break;
            case "comedy":
                result = Constants.COMEDY_BASE_AMOUNT;
                if (performance.getAudience() > Constants.COMEDY_AUDIENCE_THRESHOLD) {
                    result += Constants.COMEDY_OVER_BASE_CAPACITY_AMOUNT
                            + (Constants.COMEDY_OVER_BASE_CAPACITY_PER_PERSON
                            * (performance.getAudience() - Constants.COMEDY_AUDIENCE_THRESHOLD));
                }
                result += Constants.COMEDY_AMOUNT_PER_AUDIENCE * performance.getAudience();
                break;
            default:
                throw new RuntimeException(String.format("unknown type: %s", getPlay(performance).getType()));
        }
        return result;
    }

}
