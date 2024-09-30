package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONArray;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    private final Map<String, List<String>> countryLanguages;
    private final Map<String, Map<String, String>> translations;

    // TODO Task: pick appropriate instance variables for this class

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {

        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {

        countryLanguages = new HashMap<>();
        translations = new HashMap<>();
        // read the file to get the data to populate things...
        try {
            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));
            JSONArray jsonArray = new JSONArray(jsonString); // Directly parse the string into a JSONArray

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject countryData = jsonArray.getJSONObject(i);
                String countryName = countryData.getString("en"); // Get the English name as the key

                // Create a map for translations
                Map<String, String> countryTranslations = new HashMap<>();

                // Iterate through all keys (language codes)
                for (String key : countryData.keySet()) {
                    // Exclude 'id', 'alpha2', and 'alpha3', since you only want translations
                    if (!key.equals("id") && !key.equals("alpha2") && !key.equals("alpha3") && !key.equals("en")) {
                        countryTranslations.put(key, countryData.getString(key));
                    }
                }

                // Store the translations and add country name as the key for languages
                translations.put(countryName, countryTranslations);
                countryLanguages.put(countryName, new ArrayList<>(countryTranslations.keySet())); // Store language codes
            }
        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        // TODO Task: return an appropriate list of language codes,
        //            but make sure there is no aliasing to a mutable object
        return countryLanguages.containsKey(country) ? new ArrayList<>(countryLanguages.get(country)) : new ArrayList<>();
    }

    @Override
    public List<String> getCountries() {
        // TODO Task: return an appropriate list of country codes,
        //            but make sure there is no aliasing to a mutable object
        return new ArrayList<>(countryLanguages.keySet());
    }

    @Override
    public String translate(String country, String language) {

        if (translations.containsKey(country)) {
            Map<String, String> countryTranslationMap = translations.get(country);
            if (countryTranslationMap.containsKey(language)) {
                return countryTranslationMap.get(language);
            }
        }
        // TODO Task: complete this method using your instance variables as needed
        return null;
    }
}
