package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;


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
            // Read the JSON file content into a string
            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));
            JSONArray jsonArray = new JSONArray(jsonString);

            // Iterate over each country object in the JSON array
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject countryData = jsonArray.getJSONObject(i);

                // Fetch the country name in English
                String countryName = countryData.getString("en");

                // Prepare a map to store translations for this country
                Map<String, String> countryTranslations = new HashMap<>();
                List<String> languages = new ArrayList<>();

                // Iterate over all key-value pairs in the country object
                for (String key : countryData.keySet()) {
                    // Skip non-language keys
                    if (!key.equals("id") && !key.equals("alpha2") && !key.equals("alpha3")) {
                        countryTranslations.put(key, countryData.getString(key));
                        languages.add(key);
                    }
                }

                countryLanguages.put(countryName, languages);

                // Store the translation map for the country
                translations.put(countryName, countryTranslations);
            }
        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        // TODO Task: return an appropriate list of language codes,
        //            but make sure there is no aliasing to a mutable object
        return countryLanguages.containsKey(country) ? new ArrayList<>
                (countryLanguages.get(country)) : new ArrayList<>();
    }

    @Override
    public List<String> getCountries() {
        // TODO Task: return an appropriate list of country codes,
        //            but make sure there is no aliasing to a mutable object
        return new ArrayList<>(countryLanguages.keySet());
    }

    @Override
    public String translate(String country, String language) {
        String normalizedCountry = country.trim();

        if (translations.containsKey(normalizedCountry)) {
            Map<String, String> countryTranslationMap = translations.get(normalizedCountry);
            return countryTranslationMap.getOrDefault(language, null);
        }
        return null;
    }
}
