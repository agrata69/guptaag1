package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * This class provides the service of converting country codes to their names.
 */
public class CountryCodeConverter {
    private final Map<String, String> codeToCountryMap = new HashMap<>();
    private final Map<String, String> countryToCodeMap = new HashMap<>();

    // TODO Task: pick appropriate instance variable(s) to store the data necessary for this class

    /**
     * Default constructor which will load the country codes from "country-codes.txt"
     * in the resources folder.
     */
    public CountryCodeConverter() {

        this("country-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the country code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public CountryCodeConverter(String filename) {

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            for (String line : lines.subList(1, lines.size())) {
                String[] columns = line.split("\t");

                // Ensure columns array has enough data
                if (columns.length >= 3) {
                    String country = columns[0].trim(); // Get the country name
                    String alpha3Code = columns[2].trim(); // Get the Alpha-3 code

                    // Populate the maps
                    codeToCountryMap.put(alpha3Code, country);
                    countryToCodeMap.put(country, alpha3Code);
                }
            }
        }catch (IOException | URISyntaxException ex) {
            throw new RuntimeException("Error reading country codes file", ex);
        }

    }

    /**
     * Returns the name of the country for the given country code.
     * @param code the 3-letter code of the country
     * @return the name of the country corresponding to the code
     */
    public String fromCountryCode(String code) {
        // TODO Task: update this code to use an instance variable to return the correct value
        return codeToCountryMap.getOrDefault(code.toUpperCase(), "Unknown country code");
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        // TODO Task: update this code to use an instance variable to return the correct value
        return countryToCodeMap.getOrDefault(country, "Unknown country");
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        // TODO Task: update this code to use an instance variable to return the correct value
        return codeToCountryMap.size();
    }
}
