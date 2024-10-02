package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides the service of converting language codes to their names.
 */
public class LanguageCodeConverter {

    private final Map<String, String> languageMap;

    // TODO Task: pick appropriate instance variables to store the data necessary for this class

    /**
     * Default constructor which will load the language codes from "language-codes.txt"
     * in the resources folder.
     */
    public LanguageCodeConverter() {
        this(
                "language-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the language code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public LanguageCodeConverter(String filename) {
        languageMap = new HashMap<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            // Skip the header line
            for (String line : lines) {
                line = line.trim(); // Trim leading/trailing spaces

                // Split the line based on tab characters
                String[] parts = line.split("\t");

                // Ensure we have exactly two parts (language name and code)
                if (parts.length == 2) {
                    String name = parts[0].trim(); // Language name
                    String code = parts[1].trim(); // ISO language code

                    // Add to the map
                    languageMap.put(code, name);
                }
            }

        }  catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Returns the name of the language for the given language code.
     * @param code the language code
     * @return the name of the language corresponding to the code
     */
    public String fromLanguageCode(String code) {
        // TODO Task: update this code to use your instance variable to return the correct value
        return languageMap.getOrDefault(code, "Language not found");
    }

    /**
     * Returns the code of the language for the given language name.
     * @param language the name of the language
     * @return the 2-letter code of the language
     */
    public String fromLanguage(String language) {
        // TODO Task: update this code to use your instance variable to return the correct value
        for (Map.Entry<String, String> entry : languageMap.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(language)) {
                return entry.getKey(); // Return the code if the language matches
            }
        }
        return "Code not found";
    }
    /**
     * Returns how many languages are included in this code converter.
     * @return how many languages are included in this code converter.
     */
    public int getNumLanguages() {
        // TODO Task: update this code to use your instance variable to return the correct value
        return languageMap.size();
    }
}
